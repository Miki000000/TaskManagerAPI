package com.api.task_manager.features.tasks

import com.api.task_manager.features.user.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Table(name = "tasks")
@Entity(name = "tasks")
data class Task(
    val title: String,
    val description: String,
    val companies: MutableList<String?>,
    val completed: Boolean,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attributed_user_id")
    val attributedUser: User,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    val createdBy: User,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val creationDate: LocalDateTime = LocalDateTime.now()
)