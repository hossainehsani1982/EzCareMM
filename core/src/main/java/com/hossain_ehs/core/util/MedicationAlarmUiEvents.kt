package com.hossain_ehs.core.util

sealed class MedicationAlarmUiEvents{
   object OnDataReady : MedicationAlarmUiEvents()
   data class OnUserDataReady(val uri: String,
                              val useName : String) : MedicationAlarmUiEvents()
    object OnSlideComplete : MedicationAlarmUiEvents()
}