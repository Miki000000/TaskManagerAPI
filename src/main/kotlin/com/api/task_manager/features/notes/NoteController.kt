package com.api.task_manager.features.notes

import com.api.task_manager.features.notes.commands.*
import com.api.task_manager.features.notes.queries.ReadAllNotesQueryHandler
import com.api.task_manager.features.notes.queries.ReadNoteQueryHandler
import com.api.task_manager.features.notes.queries.ReadNotesQueryHandler
import com.api.task_manager.features.user.User
import com.api.task_manager.shared.handleResponse
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/note")
class NoteController(
    private val createNoteCommandHandler: CreateNoteCommandHandler,
    private val deleteNoteCommand: DeleteNoteCommand,
    private val updateNoteCommandHandler: UpdateNoteCommandHandler,
    private val getNotesQueryHandler: ReadNotesQueryHandler,
    private val readAllNotesQueryHandler: ReadAllNotesQueryHandler,
    private val readNoteQueryHandler: ReadNoteQueryHandler
) {
    @PostMapping("")
    fun createNoteRequest(
        @RequestBody @Valid command: CreateNoteCommand,
        @AuthenticationPrincipal user: User
    ) = createNoteCommandHandler.createNote(user, command).handleResponse()

    @DeleteMapping("/{id}")
    fun deleteNoteRequest(
        @PathVariable id: Long
    ) = deleteNoteCommand.deleteNote(id).handleResponse()

    @PutMapping("/{id}")
    fun putNoteRequest(
        @PathVariable id: Long,
        @RequestBody @Valid command: UpdateNoteCommand
    ) = updateNoteCommandHandler.updateNote(id, command).handleResponse()

    @PatchMapping("/{id}")
    fun patchNoteRequest(
        @PathVariable id: Long,
        @RequestBody @Valid command: UpdatePartialNoteCommand
    ) = updateNoteCommandHandler.updatePartialNote(id, command)

    @GetMapping("")
    fun getNotesRequest(
        @AuthenticationPrincipal user: User
    ) = getNotesQueryHandler.readNotes(user.id!!).handleResponse()

    @GetMapping("/all")
    fun getAllNotesRequest() = readAllNotesQueryHandler.readAllNotes().handleResponse()

    @GetMapping("/{id}")
    fun getNoteRequest(@PathVariable id: Long, @AuthenticationPrincipal user: User) =
        readNoteQueryHandler.readNote(user.id!!, id).handleResponse()
}