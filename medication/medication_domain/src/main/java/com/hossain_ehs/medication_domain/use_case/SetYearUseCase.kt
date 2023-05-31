package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.medication_domain.util.Constants
import com.hossain_ehs.medication_domain.util.Constants.ENGLISH_LANGUAGE
import com.hossain_ehs.medication_domain.util.Constants.GREGORIAN_CALENDAR
import com.hossain_ehs.medication_domain.util.Constants.PERSIAN_LANGUAGE
import com.hossain_ehs.medication_domain.util.Constants.PERSIAN_CALENDAR
import com.ibm.icu.util.Calendar
import com.ibm.icu.util.ULocale
import java.util.*

class SetYearUseCase(
    private val englishToPersianFormatter: EnglishToPersianFormatterUseCase
) {
    operator fun invoke(language : String) : String {
        val locale = ULocale("fa_IR@calendar=persian")
        val gregorianCalendar: Calendar = Calendar.getInstance(Locale.getDefault())
        val shamsiCalendar: Calendar = Calendar.getInstance(locale)
         when(language) {
            PERSIAN_LANGUAGE -> {
                return englishToPersianFormatter(shamsiCalendar
                    .get(Calendar.YEAR).toString())
            }
            ENGLISH_LANGUAGE -> {
                return gregorianCalendar
                    .get(Calendar.YEAR).toString()
            }

        }
        return ""
    }

}