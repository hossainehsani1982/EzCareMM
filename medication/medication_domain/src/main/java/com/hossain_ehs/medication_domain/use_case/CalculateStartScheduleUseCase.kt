package com.hossain_ehs.medication_domain.use_case

import com.ibm.icu.util.Calendar
import com.ibm.icu.util.ULocale
import java.util.*

class CalculateStartScheduleUseCase() {
    operator fun invoke(
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
    ): Long {
        val language: String = Locale.getDefault().displayLanguage
        val locale = ULocale("fa_IR@calendar=persian")
        val calendar: Calendar = Calendar.getInstance(Locale.getDefault())
        val shamsiCalendar: Calendar = Calendar.getInstance(locale)
        var startSchedule: Long

        when (language) {
            "فارسی" -> {
                shamsiCalendar.set(year, month - 1, day, hour, minute)
                val milliSeconds = shamsiCalendar.timeInMillis
                shamsiCalendar.timeInMillis = milliSeconds
                startSchedule = shamsiCalendar.timeInMillis

            }
            else -> {
                calendar.set(year, month - 1, day, hour, minute)
                val milliSeconds = calendar.timeInMillis
                calendar.timeInMillis = milliSeconds
                startSchedule = calendar.timeInMillis
            }
        }
        return startSchedule
    }

}