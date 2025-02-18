package com.api.task_manager.features.user

import com.api.task_manager.features.calls.Call
import com.api.task_manager.features.notes.Note
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Table(name = "users")
@Entity(name = "users")
class User(
    val email: String,
    private val username: String,
    private val password: String,
    val role: UserRoles,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val calls: MutableList<Call> = mutableListOf(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val notes: MutableList<Note> = mutableListOf(),
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = when (role) {
        UserRoles.ADMIN -> mutableListOf(SimpleGrantedAuthority("ROLE_ADMIN"), SimpleGrantedAuthority("ROLE_USER"))
        UserRoles.USER  -> mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
    }

    override fun getPassword(): String = password
    override fun getUsername(): String = username
}