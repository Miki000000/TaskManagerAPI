package com.api.task_manager.features.user.queries

import com.api.task_manager.features.user.User
import com.api.task_manager.features.user.UserRepository
import org.springframework.stereotype.Service
import java.util.*

data class GetUsersQueryResponse(val id: UUID, val username: String, val email: String)
data class GetUsernamesQueryResponse(val username: String)

private fun User.toGetUsersQueryResponse() = GetUsersQueryResponse(id!!, username, email)
private fun User.toGetUsernamesQueryResponse() = GetUsernamesQueryResponse(username)

@Service
class GetUsersQuery(val userRepository: UserRepository) {
    fun getUsers() = userRepository.findAll().map { it?.toGetUsersQueryResponse() }
    fun getUsernames() = userRepository.findAll().map { it?.toGetUsernamesQueryResponse() }
}
