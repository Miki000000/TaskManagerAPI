package com.api.task_manager.features.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): UserDetails?
    fun findByUsername(username: String): UserDetails?
    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
}