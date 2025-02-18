package com.api.task_manager.features.notes.queries

import com.api.task_manager.features.notes.Note
import com.api.task_manager.features.notes.NoteRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

data class ReadNoteResponse(
    val title: String,
    val company: String,
    val contact: String,
    val situation: String,
    val id: Long,
    val creationDate: LocalDateTime
)

private fun Note.toReadNoteResponse() = ReadNoteResponse(title, company, contact, situation, id!!, creationDate)

@Service
class ReadNoteQueryHandler(private val noteRepository: NoteRepository) {
    fun readNote(userId: UUID, id: Long): ReadNoteResponse =
        noteRepository.findByUserId(userId).find { it?.id == id }?.toReadNoteResponse()
            ?: throw Exception("Note not found")

}