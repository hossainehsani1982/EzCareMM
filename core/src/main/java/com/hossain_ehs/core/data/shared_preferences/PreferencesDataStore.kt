package com.hossain_ehs.core.data.shared_preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.datastore by preferencesDataStore("EzCare_DataStore")

data class DataPreferences(val isServiceRunning : Boolean,
                              val isSearchByName: Boolean,
                              val isSearchByDoctorName : Boolean,
                              val isSearchByShortDescription : Boolean)


class PreferencesDataStore (@ApplicationContext context: Context){
    private val datastore = context.datastore

    val preferencesFlow = datastore.data.catch{exceptions->
        if (exceptions is IOException){
            emit(emptyPreferences())
        } else{
            throw exceptions
        }
    }.map {
        val isServiceRunning = it[PreferencesKeys.SERVICE_STATE] ?: false
        val isSearchByName = it[PreferencesKeys.SEARCH_BY_NAME] ?: false
        val isSearchByDrName = it[PreferencesKeys.SEARCH_BY_DOCTOR_NAME] ?: false
        val isSearchByShortDescription = it[PreferencesKeys.SEARCH_BY_NAME_SHORT_DESCRIPTION] ?: false
        DataPreferences(isServiceRunning,
            isSearchByName,
            isSearchByDrName,
            isSearchByShortDescription)
    }

    suspend fun updateServiceState(isServiceRunning : Boolean){
        datastore.edit { preference ->
            preference[PreferencesKeys.SERVICE_STATE] = isServiceRunning
        }
    }
    suspend fun updateIsSearchByName(isByName : Boolean){
        datastore.edit { preferences ->
            preferences[PreferencesKeys.SEARCH_BY_NAME] = isByName
        }
    }
    suspend fun updateIsSearchByDoctorName(isByDoctorNAme : Boolean){
        datastore.edit { preferences ->
            preferences[PreferencesKeys.SEARCH_BY_DOCTOR_NAME] = isByDoctorNAme
        }
    }
    suspend fun updateIsSearchByShortDescription(isByShortDescription : Boolean){
        datastore.edit { preferences ->
            preferences[PreferencesKeys.SEARCH_BY_NAME_SHORT_DESCRIPTION] = isByShortDescription
        }
    }

    private object PreferencesKeys{
        val SERVICE_STATE = booleanPreferencesKey("isServiceRunning")
        val SEARCH_BY_NAME = booleanPreferencesKey("isSearchByName")
        val SEARCH_BY_DOCTOR_NAME = booleanPreferencesKey("isSearchByDoctorName")
        val SEARCH_BY_NAME_SHORT_DESCRIPTION = booleanPreferencesKey("isSearchByShortDescription")
    }

}