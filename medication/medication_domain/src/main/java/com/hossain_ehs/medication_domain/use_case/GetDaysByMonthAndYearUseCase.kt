package com.hossain_ehs.medication_domain.use_case

import com.ibm.icu.util.Calendar
import com.ibm.icu.util.ULocale
import java.util.*

class GetDaysByMonthAndYearUseCase (
    private val calculateShamsiDays: CalculateShamsiDaysUseCase,
    private val calculateGregorianDays: CalculateGregorianDaysUseCase,
    private val englishToPersianFormatter: EnglishToPersianFormatterUseCase,
    private val englishNumberFormatter: EnglishNumberFormatterUseCase
){

    operator fun invoke(language : String, month: Int, year : Int) : Array<String> {
        val days = mutableListOf<String>()
        val locale = ULocale("fa_IR@calendar=persian")
        val gregorianCalendar: Calendar = Calendar.getInstance(Locale.getDefault())
        val shamsiCalendar: Calendar = Calendar.getInstance(locale)
        when (language) {
            "فارسی" -> {
                shamsiCalendar
                    .set(Calendar.YEAR, year)
                shamsiCalendar
                    .set(Calendar.MONTH, month)

                val daysInYear = shamsiCalendar
                    .getActualMaximum(Calendar.DAY_OF_YEAR)
                val isLeap: Boolean = daysInYear == (366)

                for (i in 1..calculateShamsiDays(
                    shamsiCalendar
                        .get(Calendar.MONTH),
                    isLeap)) {
                    days.add(englishToPersianFormatter(i.toString()))
                }
            }
            else -> {
                gregorianCalendar
                    .set(Calendar.YEAR, year)
                gregorianCalendar
                    .set(Calendar.MONTH, month)
                val daysInYear = gregorianCalendar
                    .getActualMaximum(Calendar.DAY_OF_YEAR)
                val isLeap :Boolean = daysInYear == (366)
                for (i in 1..calculateGregorianDays(
                    month,
                    isLeap
                )) {
                    days.add(englishNumberFormatter(i.toString()))
                }
            }
        }
        return days.toTypedArray()
    }
}