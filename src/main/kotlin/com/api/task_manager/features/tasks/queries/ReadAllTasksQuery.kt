package com.api.task_manager.features.tasks.queries

import com.api.task_manager.features.tasks.TaskRepository
import org.springframework.stereotype.Service

@Service
class ReadAllTasksQueryHandler(private val taskRepository: TaskRepository) {
    fun getAllTasks() = taskRepository.findAll().map { task -> task?.toReadTasksQueryResponse() }
}