package com.hossain_ehs.medication_domain.use_case

import com.ibm.icu.util.Calendar
import com.ibm.icu.util.ULocale
import java.time.Year
import java.util.*

class CalculateNextScheduleUseCase (
    private val gregorianDaysUseCase: CalculateGregorianDaysUseCase
) {
    operator fun invoke(
        currentSchedule: Long,
        intervalUnit: Int,
        intervalValue: Int,
        language: String
    ): Long {

        val year: Int
        val month: Int
        val day: Int
        val hour: Int
        val minute: Int
        val locale = ULocale("fa_IR@calendar=persian")
        val gregorianCalendar: Calendar = Calendar.getInstance(Locale.getDefault())
        val shamsiCalendar: Calendar = Calendar.getInstance(locale)
        when (language) {
            "فارسی" -> {
                shamsiCalendar.timeInMillis = currentSchedule
                year = shamsiCalendar.get(Calendar.YEAR)
                month = shamsiCalendar.get(Calendar.MONTH)
                day = shamsiCalendar.get(Calendar.DAY_OF_MONTH)
                hour = shamsiCalendar.get(Calendar.HOUR_OF_DAY)
                minute = shamsiCalendar.get(Calendar.MINUTE)
            }
            else -> {
                gregorianCalendar.timeInMillis = currentSchedule
                year = gregorianCalendar.get(Calendar.YEAR)
                month = gregorianCalendar.get(Calendar.MONTH)
                day = gregorianCalendar.get(Calendar.DAY_OF_MONTH)
                hour = gregorianCalendar.get(Calendar.HOUR_OF_DAY)
                minute = gregorianCalendar.get(Calendar.MINUTE)
            }
        }



        when (intervalUnit) {
            //minutes
            0 -> {
                return when (language) {
                    "فارسی" -> {
                        shamsiCalendar.set(year, month, day, hour, minute)
                        shamsiCalendar.add(Calendar.MINUTE, intervalValue)
                        shamsiCalendar.timeInMillis
                    }
                    else -> {
                        gregorianCalendar.set(year, month, day, hour, minute)
                        gregorianCalendar.add(Calendar.MINUTE, intervalValue)
                        gregorianCalendar.timeInMillis
                    }
                }

            }
            //hour
            1 -> {
                return when (language) {
                    "فارسی" -> {
                        shamsiCalendar.set(year, month, day, hour, minute)
                        shamsiCalendar.add(Calendar.HOUR_OF_DAY, intervalValue)
                        shamsiCalendar.timeInMillis
                    }
                    else -> {
                        gregorianCalendar.set(year, month , day, hour, minute)
                        gregorianCalendar.add(Calendar.HOUR_OF_DAY, intervalValue)
                        gregorianCalendar.timeInMillis

                    }
                }

            }
            //day
            2 -> {
                return when (language) {
                    "فارسی" -> {
                        shamsiCalendar.set(year, month, day, hour, minute)
                        shamsiCalendar.add(Calendar.DAY_OF_MONTH, intervalValue)
                        shamsiCalendar.timeInMillis
                    }
                    else -> {
                        gregorianCalendar.set(year, month, day, hour, minute)
                        gregorianCalendar.
                        add(Calendar.DAY_OF_MONTH, intervalValue)
                        gregorianCalendar.timeInMillis
                    }
                }

            }
            //month
            3 -> {
                return when (language) {
                    "فارسی" -> {
                        shamsiCalendar.set(year, month, day)
                        if (shamsiCalendar.get(Calendar.MONTH) < 7
                            && gregorianDaysUseCase(
                                shamsiCalendar.get(Calendar.MONTH),
                                Year.isLeap(year.toLong())
                            ) == 30
                        ) {
                            shamsiCalendar.set(year, month, day, hour, minute)
                        } else if (shamsiCalendar.get(Calendar.MONTH) > 6
                            && gregorianDaysUseCase(
                                shamsiCalendar.get(Calendar.MONTH),
                                Year.isLeap(year.toLong())
                            ) == 31
                        ) {
                            shamsiCalendar
                                .set(year, month, day - 1, hour, minute)
                        }
                        shamsiCalendar.add(Calendar.MONTH, intervalValue)
                        shamsiCalendar.timeInMillis
                    }
                    else -> {
                        gregorianCalendar.set(year, month, day, hour, minute)
                        gregorianCalendar.add(Calendar.MONTH, intervalValue)
                        gregorianCalendar.timeInMillis
                    }

                }


            }
            //year
            4 -> {
                return when (language) {
                    "فارسی" -> {
                        shamsiCalendar.set(year, month, day, hour, minute)
                        shamsiCalendar.add(Calendar.YEAR, intervalValue)
                        shamsiCalendar.timeInMillis
                    }
                    else -> {
                        gregorianCalendar.set(year, month, day, hour, minute)
                        gregorianCalendar.add(Calendar.YEAR, intervalValue)
                        gregorianCalendar.timeInMillis
                    }
                }

            }
        }
        return 0

    }
}

