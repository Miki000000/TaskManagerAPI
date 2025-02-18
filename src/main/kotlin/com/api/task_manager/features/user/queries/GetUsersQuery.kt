package com.api.task_manager.features.user.queries

import com.api.task_manager.features.user.User
import com.api.task_manager.features.user.UserRepository
import org.springframework.stereotype.Service
import java.util.*

data class GetUsersQueryResponse(val id: UUID, val username: String, val email: String)

private fun User.toGetUsersQueryResponse() = GetUsersQueryResponse(id!!, username, email)

@Service
class GetUsersQuery(val userRepository: UserRepository) {
    fun getUsers(): List<GetUsersQueryResponse> {
        val users = userRepository.findAll()
        return users.map { user -> user.toGetUsersQueryResponse() }
    }
}
