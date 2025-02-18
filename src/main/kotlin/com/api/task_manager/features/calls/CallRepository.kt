package com.api.task_manager.features.calls

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CallRepository : JpaRepository<Call, Long> {
    fun findByUserId(userId: UUID): List<Call?>
}