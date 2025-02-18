package com.api.task_manager.features.user

enum class UserRoles(private val role: String) {
    ADMIN("admin"),
    USER("user");

    fun getRole(): String = role
}