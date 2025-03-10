package com.api.task_manager.features.tasks.queries

import com.api.task_manager.features.tasks.TaskRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ReadTaskQueryHandler(private val taskRepository: TaskRepository) {
    fun getTask(taskId: Long) =
        taskRepository.findByIdOrNull(taskId)?.toReadTasksQueryResponse() ?: throw Exception("Task not found.")
}