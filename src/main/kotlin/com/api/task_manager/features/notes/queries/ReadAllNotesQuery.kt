package com.api.task_manager.features.notes.queries

import com.api.task_manager.features.notes.Note
import com.api.task_manager.features.notes.NoteRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

data class ReadAllNotesResponse(
    val title: String,
    val company: String,
    val contact: String,
    val situation: String,
    val userId: UUID,
    val username: String,
    val id: Long,
    val creationDate: LocalDateTime
)

private fun Note.toReadAllNotesResponse() =
    ReadAllNotesResponse(title, company, contact, situation, user.id!!, user.username, id!!, creationDate)

@Service
class ReadAllNotesQueryHandler(private val noteRepository: NoteRepository) {
    fun readAllNotes(): List<ReadAllNotesResponse?> = noteRepository.findAll().map { it?.toReadAllNotesResponse() }
}