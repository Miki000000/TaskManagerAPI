package com.api.task_manager.features.notes.commands

import com.api.task_manager.features.notes.NoteRepository
import org.springframework.stereotype.Service

@Service
class DeleteNoteCommand(private val noteRepository: NoteRepository) {
    fun deleteNote(id: Long) = noteRepository.deleteById(id)
}