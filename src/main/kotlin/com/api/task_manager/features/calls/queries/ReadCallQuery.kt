package com.api.task_manager.features.calls.queries

import com.api.task_manager.features.calls.Call
import com.api.task_manager.features.calls.CallRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

data class ReadCallQueryResponse(
    val problem: String,
    val solution: String,
    val name: String,
    val company: String,
    val userId: UUID,
    val id: Long,
    val creationDate: LocalDateTime
)

@Service
class ReadCallQueryHandler(private val callRepository: CallRepository) {
    private fun Call.toReadCallQueryResponse() =
        ReadCallQueryResponse(problem, solution, name, company, user.id!!, id!!, creationDate)

    fun readCall(userId: UUID, id: Long): ReadCallQueryResponse =
        callRepository.findByUserId(userId).find { it?.id == id }?.toReadCallQueryResponse()
            ?: throw IllegalArgumentException("Not found a call with the id: $id")

}