package com.api.task_manager.features.user

import com.api.task_manager.features.user.queries.GetUsersQuery
import com.api.task_manager.shared.handleResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/user")
class UserController(val getUsersQuery: GetUsersQuery) {
    data class GetUserQueryResponse(val email: String, val username: String)

    private fun User.toGetUserQueryResponse() = GetUserQueryResponse(email, username)

    @GetMapping
    fun getUser(@AuthenticationPrincipal user: User): ResponseEntity<GetUserQueryResponse> =
        ResponseEntity.ok(user.toGetUserQueryResponse())

    @GetMapping("/all")
    fun getUsers() = getUsersQuery.getUsers().handleResponse()

    @GetMapping("/usernames")
    fun getUsernames() = getUsersQuery.getUsernames().handleResponse()
}