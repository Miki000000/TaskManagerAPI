package com.api.task_manager.configurations.security

import com.api.task_manager.features.user.User
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TokenService(@Value("\${api.security.secret}") private val secret: String) {
    fun generateToken(user: User): String {
        return runCatching {
            val algorithm = Algorithm.HMAC256(secret)!!
            JWT.create()
                .withIssuer("auth-api")
                .withSubject(user.username)
                .withExpiresAt(getExpirationDate())
                .sign(algorithm)
        }.fold(
            onSuccess = { it },
            onFailure = {
                when (it) {
                    is JWTCreationException -> throw RuntimeException("Token couldn't be created.", it)
                    else                    -> throw Exception("Server error.")
                }
            }
        )
    }

    fun validateToken(token: String): String {
        return runCatching {
            val algorithm = Algorithm.HMAC256(secret)!!
            JWT.require(algorithm)
                .withIssuer("auth-api")
                .build()
                .verify(token)
                .subject
        }.fold(
            onSuccess = { it },
            onFailure = { "" }
        )
    }

    private fun getExpirationDate(): Instant = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"))
}