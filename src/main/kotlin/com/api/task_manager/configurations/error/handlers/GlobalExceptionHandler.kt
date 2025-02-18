package com.api.task_manager.configurations.error.handlers

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleInvalidExceptions(ex: MethodArgumentNotValidException): Map<String, String> {
        val errors = mutableMapOf<String, String>()
        ex.bindingResult.fieldErrors.forEach { error ->
            errors[error.field] = error.defaultMessage ?: "Validation error"
        }
        return errors.toMap()
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonParseException(ex: HttpMessageNotReadableException): Map<String, String> {
        val cause = ex.rootCause
        val errors = mutableMapOf<String, String>(
            "success" to "false"
        )
        if (cause is UnrecognizedPropertyException) {
            errors["error"] = "Error on finding field"
            errors["message"] = "Unknown field: '${cause.propertyName}'"
        } else {
            errors["error"] = "Error in Json Formatting: ${cause?.message}"
        }
        return errors.toMap()
    }
}