package com.api.task_manager.features.tasks.queries

import com.api.task_manager.features.tasks.Task
import java.time.LocalDateTime

data class ReadTasksQueryResponse(
    val title: String,
    val description: String,
    val companies: List<String?>,
    val completed: Boolean,
    val attributedUser: String,
    val createdBy: String,
    val id: Long,
    val creationDate: LocalDateTime
)


fun Task.toReadTasksQueryResponse() = ReadTasksQueryResponse(
    title,
    description,
    companies,
    completed,
    attributedUser.username,
    createdBy.username,
    id!!,
    creationDate
)