package com.api.task_manager.features.calls.commands

import com.api.task_manager.features.calls.CallRepository
import org.springframework.stereotype.Service

@Service
class DeleteCallCommand(val callRepository: CallRepository) {
    fun deleteCall(id: Long) = callRepository.deleteById(id);
}