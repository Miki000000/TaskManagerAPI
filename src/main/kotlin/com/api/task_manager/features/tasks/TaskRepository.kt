package com.api.task_manager.features.tasks

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TaskRepository : JpaRepository<Task, Long> {
    fun findByAttributedUser_Id(userId: UUID): List<Task?>
    fun findByCreatedBy_IdAndAttributedUser_Id(attributedUserId: UUID, userId: UUID): List<Task?>
    fun findByCreatedBy_Id(userId: UUID): List<Task?>
    fun findByCreatedBy_IdAndCompleted(userId: UUID, completed: Boolean): List<Task?>
}