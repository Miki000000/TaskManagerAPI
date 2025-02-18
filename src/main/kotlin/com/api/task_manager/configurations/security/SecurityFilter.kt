package com.api.task_manager.configurations.security

import com.api.task_manager.features.user.UserRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SecurityFilter(val tokenService: TokenService, val userRepository: UserRepository) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = recoverToken(request)
            val subject = tokenService.validateToken(token)
            userRepository.findByUsername(subject)
                ?.let { userDetails ->
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                }?.let { authentication ->
                    SecurityContextHolder.getContext().authentication = authentication
                }
        } catch (ex: Exception) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = "application/json"
            response.writer.write("""{ "message": "${ex.message}" }""")
            return
        }


        filterChain.doFilter(request, response)
    }

    private fun recoverToken(request: HttpServletRequest): String {
        val authHeader = request.getHeader("Authorization")
        return authHeader?.replace("Bearer ", "") ?: ""
    }
}