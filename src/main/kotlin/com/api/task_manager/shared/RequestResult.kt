package com.api.task_manager.shared

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


fun <T> T.handleResponse(
    httpStatus: HttpStatus? = HttpStatus.OK,
): ResponseEntity<T> =
    ResponseEntity.status(httpStatus!!).body(this)