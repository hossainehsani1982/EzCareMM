package com.hossain_ehs.medication_domain.use_case


import kotlin.math.ceil

class GetNumberOfInsertIterationsUseCase {
    operator fun invoke(
        approximateDays: Int,
        cycleUnit: Int,
        cycleVal: Int
    ): Int {
        return when (cycleUnit) {
            //minutes
            0 -> {
                val minutesInDay = 1440
                ceil((approximateDays.toDouble() * minutesInDay) / cycleVal).toInt()
            }
            //hour
            1 -> {
                val hoursInDay = 24
                ceil((approximateDays.toDouble() * hoursInDay) / cycleVal).toInt()
            }
            //day
            2 -> {
                approximateDays
            }
            //month
            3 -> {
                approximateDays
            }
            //year
            4 -> {
                approximateDays
            }
            else -> 0
        }
    }
}