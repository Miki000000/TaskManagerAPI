package com.api.task_manager.features.calls

import com.api.task_manager.features.user.User
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.time.LocalDateTime

@Table(name = "calls")
@Entity(name = "calls")
data class Call(
    val problem: String,
    val solution: String,
    val name: String,
    val company: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val user: User,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val creationDate: LocalDateTime = LocalDateTime.now()
)