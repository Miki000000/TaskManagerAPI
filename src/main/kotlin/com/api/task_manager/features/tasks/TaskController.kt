package com.api.task_manager.features.tasks

import com.api.task_manager.features.tasks.commands.*
import com.api.task_manager.features.tasks.queries.ReadAllTasksQueryHandler
import com.api.task_manager.features.tasks.queries.ReadCompletedTasksQueryHandler
import com.api.task_manager.features.tasks.queries.ReadTaskQueryHandler
import com.api.task_manager.features.tasks.queries.ReadTasksQueryHandler
import com.api.task_manager.features.user.User
import com.api.task_manager.shared.handleResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/task")
class TaskController(
    private val createTaskCommandHandler: CreateTaskCommandHandler,
    private val deleteTaskCommandHandler: DeleteTaskCommandHandler,
    private val updateTaskCommandHandler: UpdateTaskCommandHandler,
    private val readTasksQueryHandler: ReadTasksQueryHandler,
    private val readTaskQueryHandler: ReadTaskQueryHandler,
    private val readAllTasksQueryHandler: ReadAllTasksQueryHandler,
    private val readCompletedTasksQueryHandler: ReadCompletedTasksQueryHandler
) {
    @PostMapping
    fun createTask(@AuthenticationPrincipal user: User, @RequestBody @Valid createTaskCommand: CreateTaskCommand) =
        createTaskCommandHandler.createTask(user, createTaskCommand).handleResponse(
            HttpStatus.CREATED
        )

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Long) = deleteTaskCommandHandler.deleteTask(id).handleResponse(HttpStatus.OK)

    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: Long, @RequestBody @Valid updateTaskCommand: UpdateTaskCommand) =
        updateTaskCommandHandler.updateTask(id, updateTaskCommand).handleResponse()

    @PatchMapping("/{id}")
    fun updatePartialTask(
        @PathVariable id: Long,
        @RequestBody @Valid updatePartialTaskCommand: UpdatePartialTaskCommand
    ) =
        updateTaskCommandHandler.updatePartialTask(id, updatePartialTaskCommand).handleResponse()

    @GetMapping("/created")
    fun getCreatedUserTasks(@AuthenticationPrincipal user: User) =
        readTasksQueryHandler.readTasks(user).handleResponse()

    @GetMapping("/attributed")
    fun getAttributedUserTasks(@AuthenticationPrincipal user: User) =
        readTasksQueryHandler.readTasksFromAttributed(user).handleResponse()

    @GetMapping("/user/{username}")
    fun getAttributedUserTasks(@PathVariable username: String) =
        readTasksQueryHandler.readTasksFromAttributed(username).handleResponse()

    @GetMapping("/username/{username}")
    fun readSpecificUserTasks(@PathVariable username: String) =
        readTasksQueryHandler.readTasksRelated(username).handleResponse()

    @GetMapping("/userid/{id}")
    fun getTask(@PathVariable id: Long) = readTaskQueryHandler.getTask(id).handleResponse()

    @GetMapping
    fun getRelatedTasks(@AuthenticationPrincipal user: User) =
        readTasksQueryHandler.readTasksRelated(user).handleResponse()

    @GetMapping("/all")
    fun getAllTasks() = readAllTasksQueryHandler.getAllTasks().handleResponse()

    @GetMapping("/completed")
    fun getCompletedTasks(@AuthenticationPrincipal user: User) =
        readCompletedTasksQueryHandler.readCompletedTasks(user).handleResponse()
}