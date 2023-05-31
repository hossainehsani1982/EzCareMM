package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.medication_domain.util.Constants
import com.ibm.icu.util.Calendar
import com.ibm.icu.util.ULocale
import java.util.*

class SetHourUseCase (
    private val englishToPersianFormatter: EnglishToPersianFormatterUseCase
) {
    operator fun invoke(language: String): String {
        val locale = ULocale("fa_IR@calendar=persian")
        val gregorianCalendar: Calendar = Calendar.getInstance(Locale.getDefault())
        val shamsiCalendar: Calendar = Calendar.getInstance(locale)
        when (language) {
            Constants.PERSIAN_LANGUAGE -> {
                return englishToPersianFormatter(
                    shamsiCalendar
                        .get(Calendar.HOUR_OF_DAY).toString()
                )
            }
            Constants.ENGLISH_LANGUAGE -> {
                return gregorianCalendar
                    .get(Calendar.HOUR_OF_DAY).toString()
            }

        }
        return ""
    }
}