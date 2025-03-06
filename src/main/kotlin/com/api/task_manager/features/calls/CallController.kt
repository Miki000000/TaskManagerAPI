package com.api.task_manager.features.calls

import com.api.task_manager.features.calls.commands.*
import com.api.task_manager.features.calls.queries.ReadAllCallsQueryHandler
import com.api.task_manager.features.calls.queries.ReadCallQueryHandler
import com.api.task_manager.features.calls.queries.ReadCallsQueryHandler
import com.api.task_manager.features.user.User
import com.api.task_manager.shared.handleResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

//localhost:8080/api/call

@RestController
@RequestMapping("api/call")
class CallController(
    private val createCallCommandHandler: CreateCallCommandHandler,
    private val updateCallCommandHandler: UpdateCallCommandHandler,
    private val readCallQueryHandler: ReadCallQueryHandler,
    private val readCallsQueryHandler: ReadCallsQueryHandler,
    private val readAllCallsQueryHandler: ReadAllCallsQueryHandler,
    private val deleteCallCommand: DeleteCallCommand
) {
    @Operation(summary = "Create a call in the database")
    @PostMapping("")
    fun createCallRequest(
        @RequestBody @Valid createCallCommand: CreateCallCommand,
        @AuthenticationPrincipal user: User
    ) = createCallCommandHandler.createCall(createCallCommand, user).handleResponse(HttpStatus.CREATED)

    @PutMapping("/{id}")
    @Operation(summary = "Update everything on a call")
    fun putCallRequest(
        @PathVariable id: Long,
        @RequestBody command: UpdateCallCommand
    ) = updateCallCommandHandler.updateCall(id, command).handleResponse(HttpStatus.OK)

    @Operation(summary = "Update one or more specific columns")
    @PatchMapping("/{id}")
    fun patchPartialCallRequest(
        @PathVariable id: Long,
        @RequestBody command: UpdateCallPatchCommand
    ) = updateCallCommandHandler.partialUpdateCall(id, command)

    @GetMapping("/{id}")
    @Operation(summary = "Get a specific call based on the id")
    fun getCallRequest(@PathVariable id: Long, @AuthenticationPrincipal user: User) =
        readCallQueryHandler.readCall(user.id!!, id)

    @GetMapping("")
    @Operation(summary = "Get all calls of the user")
    fun getAllUserCallRequest(@AuthenticationPrincipal user: User) =
        readCallsQueryHandler.readCalls(user.id!!)

    @GetMapping("/all")
    @Operation(summary = "Get all calls from every user")
    fun getAllCalls() = readAllCallsQueryHandler.readAll()

    @Operation(summary = "Delete a call based on ip")
    @DeleteMapping("/{id}")
    fun deleteCall(@PathVariable id: Long) = deleteCallCommand.deleteCall(id)
}