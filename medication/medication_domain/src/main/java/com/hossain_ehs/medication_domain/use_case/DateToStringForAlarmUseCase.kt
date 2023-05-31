package com.hossain_ehs.medication_domain.use_case

import com.ibm.icu.util.Calendar
import com.ibm.icu.util.ULocale
import java.util.*

class DateToStringForAlarmUseCase {
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0
    private val gregorianCalendar: Calendar = Calendar
        .getInstance(Locale.getDefault())
    private val persianCalendar: Calendar = Calendar
        .getInstance(ULocale("fa_IR@calendar=persian"))
    operator fun invoke(date: Long): String {
        val language = Locale.getDefault().displayLanguage
        when (language) {
            "فارسی" -> {
                persianCalendar.timeInMillis = date
                year = persianCalendar.get(Calendar.YEAR)
                month = persianCalendar.get(Calendar.MONTH) + 1
                day = persianCalendar.get(Calendar.DAY_OF_MONTH)
            }
            else -> {
                gregorianCalendar.timeInMillis = date
                year = gregorianCalendar.get(Calendar.YEAR)
                month = gregorianCalendar.get(Calendar.MONTH) + 1
                day = if (month == 0) {
                    0
                } else {
                    gregorianCalendar.get(Calendar.DAY_OF_MONTH)
                }
            }
        }

        hour = gregorianCalendar.get(Calendar.HOUR_OF_DAY)
        minute = gregorianCalendar.get(Calendar.MINUTE)
        return stringBuilder(year, month, day, hour, minute, language)
    }

    private fun stringBuilder(
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        min: Int,
        language: String
    ): String {

        when (language) {
            "فارسی" -> {
                val yearString = englishToPersian(year.toString())
                val monthString = if (month < 10) {
                    "${englishToPersian("0$month")} "
                } else {
                    englishToPersian("$month")
                }
                val dayString = if (day < 10) {
                    "${englishToPersian("0$day")} "
                } else {
                    englishToPersian("$day")
                }
                val hourString = if (hour < 10) {
                    "${englishToPersian("0$hour")} "
                } else {
                    englishToPersian("$hour")
                }
                val minString = if (min < 10) {
                    "${englishToPersian("0$min")} "
                } else {
                    "${englishToPersian("$min")} "
                }
                return "$dayString/$monthString/$yearString   $hourString:$minString"
            }
            else -> {
                val monthString = if (month < 10) {
                    "0$month"
                } else {
                    "$month"
                }
                val dayString = if (day < 10) {
                    "0$day"
                } else {
                    "$day"
                }
                val hourString = if (hour < 10) {
                    "0$hour"
                } else {
                    "$hour"
                }
                val minString = if (min < 10) {
                    "0$min"
                } else {
                    "$min"
                }
                return "$monthString/$dayString/$year (M/D/Y)  $hourString:$minString"
            }
        }
    }


    private fun englishToPersian(englishStr: String):String {
        var result = ""
        var en: Char
        for (ch in englishStr) {
            en = ch
            when (ch) {
                '0' -> en = '۰'
                '1' -> en = '۱'
                '2' -> en = '۲'
                '3' -> en = '۳'
                '4' -> en = '۴'
                '5' -> en = '۵'
                '6' -> en = '۶'
                '7' -> en = '۷'
                '8' -> en = '۸'
                '9' -> en = '۹'
                '/' -> en = '/'
                ':' -> en = ':'
            }
            result = "${result}$en"
        }
        if (result.length < 2){
            result = "۰$result"
        }
        return result
    }
}