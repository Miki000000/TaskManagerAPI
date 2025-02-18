package com.api.task_manager.features.calls.commands

import com.api.task_manager.features.calls.Call
import com.api.task_manager.features.calls.CallRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

data class UpdateCallCommand(
    val problem: String,
    val solution: String,
    val name: String,
    val company: String
) {
    init {
        require(problem.isNotBlank()) { "Must provide a problem" }
        require(solution.isNotBlank()) { "Must provide a solution" }
        require(name.isNotBlank()) { "Must provide a client name" }
        require(company.isNotBlank()) { "Must provide a company" }
    }
}

data class UpdateCallPatchCommand(
    val problem: String?,
    val solution: String?,
    val name: String?,
    val company: String?
)

data class UpdateCallResponse(
    val problem: String,
    val solution: String,
    val name: String,
    val company: String,
    val id: Long
)

private fun Call.toUpdateCallResponse() = UpdateCallResponse(problem, solution, name, company, id!!)


@Service
class UpdateCallCommandHandler(private val callRepository: CallRepository) {
    fun updateCall(id: Long, command: UpdateCallCommand): UpdateCallResponse {
        val newCall = callRepository.findByIdOrNull(id)?.copy(
            problem = command.problem,
            solution = command.solution,
            name = command.name,
            company = command.company
        ) ?: throw IllegalArgumentException("Call not found with id $id")
        return callRepository.save(newCall).toUpdateCallResponse()
    }

    fun partialUpdateCall(id: Long, command: UpdateCallPatchCommand): UpdateCallResponse {
        val updatedCall = callRepository.findByIdOrNull(id)?.let {
            it.copy(
                name = command.name ?: it.name,
                company = command.company ?: it.company,
                solution = command.solution ?: it.solution,
                problem = command.problem ?: it.problem
            )
        } ?: throw Exception("Call not found.")

        return callRepository.save(updatedCall).toUpdateCallResponse()
    }
}
