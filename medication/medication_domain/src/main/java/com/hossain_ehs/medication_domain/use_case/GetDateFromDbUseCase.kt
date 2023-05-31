package com.hossain_ehs.medication_domain.use_case

import com.hossain_ehs.medication_domain.model.medication.DateParts
import com.ibm.icu.util.Calendar
import com.ibm.icu.util.ULocale
import java.util.*


class GetDateFromDbUseCase (
    private val englishToPersianFormatter: EnglishToPersianFormatterUseCase
        ){
    operator fun invoke(requestedTime : DateParts,
                        dateInMillis: Long,
                        language :String) : String {
        val locale = ULocale("fa_IR@calendar=persian")
        val gregorianCalendar: Calendar = Calendar.getInstance(Locale.getDefault())
        val shamsiCalendar: Calendar = Calendar.getInstance(locale)
        when(requestedTime) {
            DateParts.YEAR -> {
                return when(language) {
                    "فارسی"-> {
                        englishToPersianFormatter(shamsiCalendar
                            .apply {
                                timeInMillis = dateInMillis
                            }.get(Calendar.YEAR).toString()
                        )
                    }
                    else -> {
                        gregorianCalendar
                            .apply {
                                timeInMillis = dateInMillis
                            }.get(Calendar.YEAR).toString()
                    }
                }
            }
            DateParts.MONTH -> {
                return when(language) {
                    "فارسی"-> {
                        englishToPersianFormatter(shamsiCalendar
                            .apply {
                                timeInMillis = dateInMillis
                            }.get(Calendar.MONTH).toString()
                        )
                    }
                    else -> {
                        gregorianCalendar
                            .apply {
                                timeInMillis = dateInMillis
                            }.get(Calendar.MONTH).toString()
                    }
                }
            }
            DateParts.DAY -> {
                return  when(language) {
                    "فارسی"-> {
                        englishToPersianFormatter(shamsiCalendar
                            .apply {
                                timeInMillis = dateInMillis
                            }.get(Calendar.DAY_OF_MONTH).toString()
                        )
                    }
                    else -> {
                        gregorianCalendar
                            .apply {
                                timeInMillis = dateInMillis
                            }.get(Calendar.DAY_OF_MONTH).toString()
                    }
                }
            }
            DateParts.HOUR -> {
                return  when(language) {
                    "فارسی"-> {
                        englishToPersianFormatter(shamsiCalendar
                            .apply {
                                timeInMillis = dateInMillis
                            }.get(Calendar.HOUR_OF_DAY).toString()
                        )
                    }
                    else -> {
                        gregorianCalendar
                            .apply {
                                timeInMillis = dateInMillis
                            }.get(Calendar.HOUR_OF_DAY).toString()
                    }
                }
            }
            DateParts.MINUTE -> {
                return  when(language) {
                    "فارسی"-> {
                        englishToPersianFormatter(shamsiCalendar
                            .apply {
                                timeInMillis = dateInMillis
                            }.get(Calendar.MINUTE).toString()
                        )
                    }
                    else -> {
                        gregorianCalendar
                            .apply {
                                timeInMillis = dateInMillis
                            }.get(Calendar.MINUTE).toString()
                    }
                }
            }
        }
    }
}