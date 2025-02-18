package com.api.task_manager.features.notes

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface NoteRepository : JpaRepository<Note, Long> {
    fun findByUserId(userId: UUID): List<Note?>
}