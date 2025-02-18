package com.api.task_manager.features.notes.queries

import com.api.task_manager.features.notes.Note
import com.api.task_manager.features.notes.NoteRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

data class ReadNotesResponse(
    val title: String,
    val company: String,
    val contact: String,
    val situation: String,
    val id: Long,
    val creationDate: LocalDateTime
)

private fun Note.toReadNotesResponse() = ReadNotesResponse(title, company, contact, situation, id!!, creationDate)

@Service
class ReadNotesQueryHandler(private val noteRepository: NoteRepository) {
    fun readNotes(userId: UUID): List<ReadNotesResponse?> = noteRepository.findByUserId(userId)
        .map { it?.toReadNotesResponse() }
}