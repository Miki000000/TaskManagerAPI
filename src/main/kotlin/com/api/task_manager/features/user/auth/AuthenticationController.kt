package com.api.task_manager.features.user.auth

import com.api.task_manager.configurations.security.TokenService
import com.api.task_manager.features.user.User
import com.api.task_manager.features.user.UserRepository
import com.api.task_manager.features.user.UserRoles
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val email: String, val username: String, val password: String, val role: UserRoles) {
    init {
        require(email.isNotBlank() && email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))) { "User must have a valid email." }
        require(username.isNotBlank()) { "User must have a username." }
        require(password.isNotBlank()) { "User must have a password." }
        require(password.toCharArray().size > 8) { "User must have a password with 8 characters or more." }
    }
}

data class ChangePasswordRequest(val password: String) {
    init {
        require(password.isNotBlank()) { "Must inform a password" }
        require(password.toCharArray().size > 8) { "User must have a password with 8 characters or more." }
    }
}

data class LoginResponse(val token: String)

@RestController
@RequestMapping("auth")
class AuthenticationController(
    val authenticationManager: AuthenticationManager,
    val userRepository: UserRepository,
    val passwordEncoder: BCryptPasswordEncoder,
    val tokenService: TokenService
) {

    @PostMapping("/login")
    fun authenticateUser(@RequestBody @Valid loginRequest: LoginRequest): ResponseEntity<LoginResponse> =
        userRepository.findByEmail(loginRequest.email)?.let { user ->
            runCatching {
                val usernamePassword = UsernamePasswordAuthenticationToken(user.username, loginRequest.password)
                val auth = authenticationManager.authenticate(usernamePassword)
                tokenService.generateToken(auth.principal as User)
            }.fold(
                onSuccess = { ResponseEntity.ok(LoginResponse(it)) },
                onFailure = { ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() }
            )
        } ?: ResponseEntity.notFound().build()

    @PostMapping("/password")
    fun authenticateUser(
        @AuthenticationPrincipal user: User,
        @RequestBody @Valid password: ChangePasswordRequest
    ): ResponseEntity<Unit> =
        userRepository.runCatching {
            val changedUser = User(
                user.email,
                user.username,
                passwordEncoder.encode(password.password),
                user.role,
                user.calls,
                user.notes,
                user.id!!
            )
            save(changedUser)
        }.fold(
            onSuccess = { ResponseEntity.status(HttpStatus.ACCEPTED).build() },
            onFailure = { ResponseEntity.badRequest().build() }
        )

    @PostMapping("/register")
    fun registerUser(@RequestBody @Valid registerRequest: RegisterRequest): ResponseEntity<Unit> {
        if (userRepository.existsByUsername(registerRequest.username) || userRepository.existsByEmail(registerRequest.email))
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        return runCatching {
            val user = User(
                registerRequest.email,
                registerRequest.username,
                passwordEncoder.encode(registerRequest.password),
                registerRequest.role
            )
            userRepository.save(user)
        }.fold(
            onSuccess = { ResponseEntity.status(HttpStatus.CREATED).build() },
            onFailure = { ResponseEntity.internalServerError().build() }
        )
    }
}