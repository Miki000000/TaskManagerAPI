package com.api.task_manager.features.calls.commands

import com.api.task_manager.features.calls.Call
import com.api.task_manager.features.calls.CallRepository
import com.api.task_manager.features.user.User
import org.springframework.stereotype.Service

data class CreateCallCommand(
    val problem: String, val solution: String, val name: String, val company: String
) {
    init {
        require(problem.isNotBlank()) { "Must provide a problem" }
        require(solution.isNotBlank()) { "Must provide a solution" }
        require(name.isNotBlank()) { "Must provide a client name" }
        require(company.isNotBlank()) { "Must provide a company" }
    }
}

data class CreateCallResponse(
    val id: Long,
    val problem: String,
    val solution: String,
    val name: String,
    val company: String
)

private fun Call.toCreateCallResponse() = CreateCallResponse(id!!, problem, solution, name, company)
fun CreateCallCommand.toCall(user: User) = Call(problem, solution, name, company, user)

@Service
class CreateCallCommandHandler(
    private val callRepository: CallRepository
) {
    fun createCall(createCallCommand: CreateCallCommand, user: User): CreateCallResponse {
        val newCall = createCallCommand.toCall(user)
        return callRepository.save(newCall).toCreateCallResponse()
    }
}
