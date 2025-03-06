package com.api.task_manager.features.calls.queries

import com.api.task_manager.features.calls.Call
import com.api.task_manager.features.calls.CallRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

data class ReadAllCallsQuery(
    val problem: String,
    val solution: String,
    val name: String,
    val company: String,
    val userId: UUID,
    val username: String,
    val id: Long,
    val creationDate: LocalDateTime
)

@Service
class ReadAllCallsQueryHandler(private val callRepository: CallRepository) {
    private fun Call.toReadAllCallsQuery() =
        ReadAllCallsQuery(problem, solution, name, company, user.id!!, user.username, id!!, creationDate)

    fun readAll(): List<ReadAllCallsQuery> = callRepository.findAll().map {
        it.toReadAllCallsQuery()
    }
}