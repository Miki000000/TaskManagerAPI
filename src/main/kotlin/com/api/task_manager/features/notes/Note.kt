package com.api.task_manager.features.notes

import com.api.task_manager.features.user.User
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.time.LocalDateTime

@Table(name = "notes")
@Entity(name = "notes")
data class Note(
    val title: String,
    val company: String,
    val contact: String,
    val situation: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val user: User,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val creationDate: LocalDateTime = LocalDateTime.now()
)