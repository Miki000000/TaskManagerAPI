package com.api.task_manager.features.tasks.commands

import com.api.task_manager.features.tasks.TaskRepository
import com.api.task_manager.features.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

data class UpdateTaskCommand(
    val title: String,
    val description: String,
    val companies: List<String?>,
    val completed: Boolean?,
    val attributedUser: String,
) {
    init {
        require(title.isNotBlank()) { "Must inform a title" }
        require(description.isNotBlank()) { "Must inform a description" }
        require(completed != null) { "Must inform a completed status" }
        require(attributedUser.isNotBlank()) { "Must inform a user" }
    }
}

data class UpdatePartialTaskCommand(
    val title: String?,
    val description: String?,
    val companies: List<String?>?,
    val completed: Boolean?,
    val attributedUser: String?,
)

@Service
class UpdateTaskCommandHandler(private val taskRepository: TaskRepository, private val userRepository: UserRepository) {
    fun updatePartialTask(taskId: Long, task: UpdatePartialTaskCommand) =
        taskRepository.findByIdOrNull(taskId)?.let {
            val updatedTask = it.copy(
                title = task.title ?: it.title,
                description = task.description ?: it.description,
                companies = task.companies?.toMutableList() ?: it.companies,
                completed = task.completed ?: it.completed,
                attributedUser = userRepository.findByUsername(task.attributedUser ?: "") ?: it.attributedUser
            )
            taskRepository.save(updatedTask)
        } ?: throw Exception("Task not found.")

    fun updateTask(taskId: Long, task: UpdateTaskCommand) =
        taskRepository.findByIdOrNull(taskId)?.let {
            taskRepository.save(
                it.copy(
                    title = task.title,
                    description = task.description,
                    companies = task.companies.toMutableList(),
                    completed = task.completed ?: it.completed,
                    attributedUser = userRepository.findByUsername(task.attributedUser)
                        ?: throw Exception("User not found.")
                )
            )
        } ?: throw Exception("Task now found")
}