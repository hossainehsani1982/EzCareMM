package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.core.domain.shared_preferences.Preferences

class SetServiceStatusUseCase(
    private val preferences: Preferences
) {
    suspend operator fun invoke(status: Boolean) {
        preferences.setServerStatus(status)
    }
}
