package com.hossain_ehs.medication_domain.model.alarm

import java.time.LocalDateTime

data class AlarmItem(
    val id : Int = 1,
    val time : LocalDateTime
)
