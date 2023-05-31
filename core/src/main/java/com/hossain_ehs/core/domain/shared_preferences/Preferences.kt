package com.hossain_ehs.core.domain.shared_preferences


import kotlinx.coroutines.flow.Flow

interface Preferences {

    suspend fun setServerStatus(status: Boolean)
    fun getServerStatus(): Flow<Boolean>

    suspend fun setSearchByName(status: Boolean)
    fun getSearchByName(): Flow<Boolean>

    suspend fun setSearchByDoctorName (status: Boolean)
    fun getSearchByDoctorName(): Flow<Boolean>

    suspend fun setSearchByShortDescription (status: Boolean)
    fun getSearchByShortDescription(): Flow<Boolean>

}