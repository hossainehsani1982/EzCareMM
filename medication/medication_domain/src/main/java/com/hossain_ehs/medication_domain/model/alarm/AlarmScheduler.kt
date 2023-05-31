package com.hossain_ehs.medication_domain.model.alarm

interface AlarmScheduler {
    fun setUserID(userId : Int)
    fun setMedicationId(medicationId : Int)
    fun schedule(item: AlarmItem)
    fun cancel(item: AlarmItem)
}