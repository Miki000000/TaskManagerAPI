package com.api.task_manager.features.tasks.queries

import com.api.task_manager.features.tasks.TaskRepository
import com.api.task_manager.features.user.User
import com.api.task_manager.features.user.UserRepository
import org.springframework.stereotype.Service

@Service
class ReadTasksQueryHandler(private val taskRepository: TaskRepository, private val userRepository: UserRepository) {
    fun readTasks(user: User) =
        taskRepository.findByCreatedBy_Id(user.id!!).map { task -> task?.toReadTasksQueryResponse() }

    fun readTasksFromAttributed(user: User): List<ReadTasksQueryResponse?> =
        taskRepository.findByAttributedUser_Id(user.id!!)
            .map { task -> task?.toReadTasksQueryResponse() }

    fun readTasksFromAttributed(username: String): List<ReadTasksQueryResponse?> =
        userRepository.findByUsername(username).let { user ->
            taskRepository.findByAttributedUser_Id(user?.id!!).map { task ->
                task?.toReadTasksQueryResponse()
            }
        }

    fun readTasksRelated(username: String): List<ReadTasksQueryResponse?> =
        userRepository.findByUsername(username).let { user ->
            taskRepository.findByCreatedBy_IdOrAttributedUser_Id(user?.id!!, user.id!!).map { task ->
                task?.toReadTasksQueryResponse()
            }
        }

    fun readTasksRelated(user: User): List<ReadTasksQueryResponse?> =
        taskRepository.findByCreatedBy_IdOrAttributedUser_Id(user.id!!, user.id!!).map { task ->
            task?.toReadTasksQueryResponse()
        }
}