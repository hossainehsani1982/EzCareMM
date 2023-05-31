package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.core.domain.shared_preferences.Preferences

class GetServiceStatusUseCase(
    private val preferences: Preferences
) {
    suspend operator fun invoke(): Boolean {
        var result = false
        preferences.getServerStatus().collect {
            result = it
        }
        return result
    }
}