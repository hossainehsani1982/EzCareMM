package com.hossain_ehs.medication_domain.use_case


import com.ibm.icu.util.Calendar
import com.ibm.icu.util.ULocale
import java.util.*

class DateToStringUseCase (
    private val englishToPersianFormatter: EnglishToPersianFormatterUseCase,
    private val englishNumberFormatter: EnglishNumberFormatterUseCase,
        ) {

    operator fun invoke(
        language: String,
        timeInMillis: Long,
        months : Array<String> = arrayOf()
    ): String {
        val locale = ULocale("fa_IR@calendar=persian")
        val shamsiCalendar: Calendar = Calendar.getInstance(locale)
        val gregorianLocale = Locale.getDefault()
        val calendar: Calendar = Calendar.getInstance(gregorianLocale)
        val months = months
        when (language) {
            "فارسی" -> {
                shamsiCalendar.timeInMillis = timeInMillis
                val hourDB = shamsiCalendar.get(Calendar.HOUR_OF_DAY)
                val minuteDB = shamsiCalendar.get(Calendar.MINUTE)
                val yearDB = shamsiCalendar.get(Calendar.YEAR)
                val monthDB = shamsiCalendar.get(Calendar.MONTH)
                val dayDB = shamsiCalendar.get(Calendar.DAY_OF_MONTH)
                return setDateString(dayDB, months[monthDB], yearDB, hourDB, minuteDB, language)
            }
            else -> {
                calendar.timeInMillis = timeInMillis
                val hourDB = calendar.get(Calendar.HOUR_OF_DAY)
                val minuteDB = calendar.get(Calendar.MINUTE)
                val yearDB = calendar.get(Calendar.YEAR)
                val monthDB = calendar.get(Calendar.MONTH)
                val dayDB = calendar.get(Calendar.DAY_OF_MONTH)
                return setDateString(dayDB, months[monthDB], yearDB, hourDB, minuteDB, language)
            }

        }
    }

    private fun setDateString(
        day: Int,
        monthString: String,
        year: Int,
        hour: Int,
        minute: Int,
        language: String
    ): String {
        val fullDate: String = when (language) {
            "فارسی" -> {
                " $englishToPersianFormatter(day.toString())} " +
                        " $monthString " +
                        " ${englishToPersianFormatter(year.toString())} - " +
                        " ${englishToPersianFormatter(minute.toString())}" +
                        " : ${englishToPersianFormatter(
                                hour.toString()
                            )
                        }"
            }
            else -> {
                " ${englishNumberFormatter(day.toString())} " +
                        " $monthString  $year - " +
                        "${englishNumberFormatter(hour.toString())} " +
                        ": ${englishNumberFormatter(
                                minute.toString()
                            )
                        }"
            }

        }
        return fullDate
    }
}
