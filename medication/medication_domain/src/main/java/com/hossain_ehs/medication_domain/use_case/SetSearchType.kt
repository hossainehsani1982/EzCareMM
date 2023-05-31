package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.core.domain.shared_preferences.Preferences
import com.hossain_ehs.medication_domain.model.medication.SearchType

class SetSearchType (
    private val preferences: Preferences
        ) {
    suspend operator fun invoke(searchType: SearchType) {
       when (searchType){
              SearchType.MEDICATION_NAME -> {
                preferences.setSearchByName(true)
                preferences.setSearchByDoctorName(false)
                preferences.setSearchByShortDescription(false)
              }
              SearchType.DOCTOR_NAME -> {
                preferences.setSearchByName(false)
                preferences.setSearchByDoctorName(true)
                preferences.setSearchByShortDescription(false)
              }
              SearchType.SHORT_DESCRIPTION -> {
                preferences.setSearchByName(false)
                preferences.setSearchByDoctorName(false)
                preferences.setSearchByShortDescription(true)
              }
       }
    }
}