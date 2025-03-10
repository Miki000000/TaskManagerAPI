package com.api.task_manager.features.tasks.queries

import com.api.task_manager.features.tasks.TaskRepository
import com.api.task_manager.features.user.User
import org.springframework.stereotype.Service

@Service
class ReadCompletedTasksQueryHandler(private val taskRepository: TaskRepository) {
    fun readCompletedTasks(user: User) =
        taskRepository.findByCreatedBy_IdAndCompleted(user.id!!, true).map { it?.toReadTasksQueryResponse() }
}