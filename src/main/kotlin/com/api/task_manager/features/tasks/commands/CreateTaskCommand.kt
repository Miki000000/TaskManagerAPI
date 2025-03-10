package com.api.task_manager.features.tasks.commands

import com.api.task_manager.features.tasks.Task
import com.api.task_manager.features.tasks.TaskRepository
import com.api.task_manager.features.user.User
import com.api.task_manager.features.user.UserRepository
import org.springframework.stereotype.Service

data class CreateTaskCommand(
    val title: String,
    val description: String,
    val companies: List<String?>,
    val completed: Boolean?,
    val attributedUser: String,
) {
    init {
        require(title.isNotBlank()) { "Must have a title" }
        require(description.isNotBlank()) { "Must have a description" }
        require(attributedUser.isNotBlank()) { "Must inform the attributed user username" }
    }
}


@Service
class CreateTaskCommandHandler(private val taskRepository: TaskRepository, private val userRepository: UserRepository) {
    fun createTask(user: User, task: CreateTaskCommand) {
        val createdByUser =
            userRepository.findByUsername(task.attributedUser) ?: throw Exception("User to attribution does not exist.")
        val task = Task(
            title = task.title,
            description = task.description,
            companies = task.companies.toMutableList(),
            completed = task.completed == true,
            attributedUser = createdByUser,
            createdBy = user
        )
        taskRepository.save(task);
    }
}