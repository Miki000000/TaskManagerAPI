package com.api.task_manager.features.notes.commands

import com.api.task_manager.features.notes.Note
import com.api.task_manager.features.notes.NoteRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

data class UpdateNoteCommand(val title: String, val company: String, val contact: String, val situation: String) {
    init {
        require(title.isNotBlank()) { "Must inform a title" }
        require(company.isNotBlank()) { "Must inform a company" }
        require(contact.isNotBlank()) { "Must inform a contact" }
        require(situation.isNotBlank()) { "Must inform a situation" }
    }
}

data class UpdatePartialNoteCommand(
    val title: String?,
    val company: String?,
    val contact: String?,
    val situation: String?
)

data class UpdateNoteResponse(
    val title: String,
    val company: String,
    val contact: String,
    val situation: String,
    val id: Long
)

private fun Note.toUpdateNoteResponse() = UpdateNoteResponse(title, company, contact, situation, id!!)

@Service
class UpdateNoteCommandHandler(private val noteRepository: NoteRepository) {
    fun updateNote(id: Long, updateNoteCommand: UpdateNoteCommand): UpdateNoteResponse {
        val updatedNote = noteRepository.findByIdOrNull(id)?.copy(
            title = updateNoteCommand.title,
            company = updateNoteCommand.company,
            contact = updateNoteCommand.contact,
            situation = updateNoteCommand.situation
        ) ?: throw Exception("Note not found")
        return noteRepository.save(updatedNote).toUpdateNoteResponse()
    }

    fun updatePartialNote(id: Long, updatePartialNoteCommand: UpdatePartialNoteCommand): UpdateNoteResponse {
        val updatedNote = noteRepository.findByIdOrNull(id)?.let {
            it.copy(
                title = updatePartialNoteCommand.title ?: it.title,
                company = updatePartialNoteCommand.company ?: it.company,
                contact = updatePartialNoteCommand.contact ?: it.contact,
                situation = updatePartialNoteCommand.situation ?: it.situation
            )
        } ?: throw Exception("Note for update not found.")
        return noteRepository.save(updatedNote).toUpdateNoteResponse()
    }
}
