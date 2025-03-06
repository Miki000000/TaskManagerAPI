package com.api.task_manager.configurations

import com.api.task_manager.features.user.User
import com.api.task_manager.features.user.UserRepository
import com.api.task_manager.features.user.UserRoles
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : CommandLineRunner {
    override fun run(vararg args: String) {
        if (!userRepository.existsByEmail("admin@admin.com")) {
            val adminUser = User(
                email = "admin@admin.com",
                username = "admin",
                password = passwordEncoder.encode("1234"),
                role = UserRoles.ADMIN
            )
            userRepository.save(adminUser)
            println("Default admin user created successfully")
        } else {
            println("Default admin user already exists")
        }
    }
}
