package com.api.task_manager.features.user.auth

import com.api.task_manager.features.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthenticationServices(val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails =
        username?.let { userRepository.findByUsername(it) }
            ?: throw UsernameNotFoundException("Username not found.")
}