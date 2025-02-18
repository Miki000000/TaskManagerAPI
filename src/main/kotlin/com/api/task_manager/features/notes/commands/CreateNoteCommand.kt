package com.api.task_manager.features.notes.commands

import com.api.task_manager.features.notes.Note
import com.api.task_manager.features.notes.NoteRepository
import com.api.task_manager.features.user.User
import org.springframework.stereotype.Service
import java.time.LocalDateTime

data class CreateNoteCommand(val title: String, val company: String, val contact: String, val situation: String) {
    init {
        require(title.isNotBlank()) { "Must have a title" }
        require(company.isNotBlank()) { "Must inform a company" }
        require(contact.isNotBlank()) { "Must inform a contact" }
        require(situation.isNotBlank()) { "Must inform a situation" }
    }
}

data class CreateNoteResponse(
    val title: String,
    val company: String,
    val contact: String,
    val situation: String,
    val id: Long,
    val creationDate: LocalDateTime
)

private fun Note.toCreateNoteResponse() = CreateNoteResponse(title, company, contact, situation, id!!, creationDate)
private fun CreateNoteCommand.toNote(user: User) = Note(title, company, contact, situation, user)

@Service
class CreateNoteCommandHandler(private val noteRepository: NoteRepository) {
    fun createNote(user: User, command: CreateNoteCommand): CreateNoteResponse {
        val newNote = command.toNote(user)
        return noteRepository.save(newNote).toCreateNoteResponse()
    }
}