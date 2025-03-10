package com.api.task_manager.features.tasks.queries

import com.api.task_manager.features.tasks.TaskRepository
import com.api.task_manager.features.user.User
import com.api.task_manager.features.user.UserRepository
import org.springframework.stereotype.Service

@Service
class ReadTasksQueryHandler(private val taskRepository: TaskRepository, private val userRepository: UserRepository) {
    fun readTasks(user: User) =
        taskRepository.findByCreatedBy_Id(user.id!!).map { task -> task?.toReadTasksQueryResponse() }

    fun readTasksFromAttributed(user: User, username: String): List<ReadTasksQueryResponse?> {
        val attributedUser = userRepository.findByUsername(username) ?: throw Exception("User does not exists.")
        return taskRepository.findByCreatedBy_IdAndAttributedUser_Id(user.id!!, attributedUser.id!!)
            .map { task -> task?.toReadTasksQueryResponse() }
    }
}