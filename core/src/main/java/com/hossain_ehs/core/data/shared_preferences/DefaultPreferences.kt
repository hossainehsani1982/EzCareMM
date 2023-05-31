package com.hossain_ehs.core.data.shared_preferences

import android.content.SharedPreferences
import com.hossain_ehs.core.domain.shared_preferences.Preferences
import com.hossain_ehs.core.local.entity.MedicationEntity

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DefaultPreferences(
    private val preferencesDataStore: PreferencesDataStore,
) : Preferences
 {
     override suspend fun setServerStatus(status: Boolean) {
         preferencesDataStore.updateServiceState(status)
     }
     override fun getServerStatus(): Flow<Boolean> {
            return preferencesDataStore.preferencesFlow.map {
                it.isServiceRunning }
     }

     override suspend fun setSearchByName(status: Boolean) {
         preferencesDataStore.updateIsSearchByName(status)
     }
     override fun getSearchByName(): Flow<Boolean>  {
            return preferencesDataStore.preferencesFlow.map {
                it.isSearchByName }
     }

     override fun getSearchByDoctorName(): Flow<Boolean>  {
            return preferencesDataStore.preferencesFlow.map {
                it.isSearchByDoctorName }
     }
     override suspend fun setSearchByDoctorName(status: Boolean) {
            preferencesDataStore.updateIsSearchByDoctorName(status)
     }



     override suspend fun setSearchByShortDescription(status: Boolean) {
            preferencesDataStore.updateIsSearchByShortDescription(status)
     }
     override fun getSearchByShortDescription(): Flow<Boolean>  {
            return preferencesDataStore.preferencesFlow.map {
                it.isSearchByShortDescription }
     }

 }