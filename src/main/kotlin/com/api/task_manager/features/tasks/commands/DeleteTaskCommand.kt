package com.api.task_manager.features.tasks.commands

import com.api.task_manager.features.tasks.TaskRepository
import org.springframework.stereotype.Service

@Service
class DeleteTaskCommandHandler(private val noteRepository: TaskRepository) {
    fun deleteTask(taskId: Long) = noteRepository.deleteById(taskId)
}