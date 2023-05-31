package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.medication_domain.model.alarm.AlarmScheduler

class ProvideAlarmSchedulerUseCase (
    private val alarmScheduler: AlarmScheduler
        ) {
    operator fun invoke(): AlarmScheduler {
        return alarmScheduler
    }
}
