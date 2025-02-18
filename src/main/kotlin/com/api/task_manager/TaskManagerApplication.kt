package com.api.task_manager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@EntityScan(
    basePackages = [
        "com.api.task_manager.features.user",
        "com.api.task_manager.features.calls",
        "com.api.task_manager.features.notes"
    ]
)
@SpringBootApplication
class TaskManagerApplication

fun main(args: Array<String>) {
    runApplication<TaskManagerApplication>(*args)
}
