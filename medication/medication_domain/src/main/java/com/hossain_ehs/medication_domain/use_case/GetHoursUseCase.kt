package com.hossain_ehs.medication_domain.use_case

class GetHoursUseCase (
    private val englishToPersianFormatter: EnglishToPersianFormatterUseCase,
    private val englishNumberFormatter: EnglishNumberFormatterUseCase
        ) {
    operator fun invoke(language : String) : Array<String> {
        val days = mutableListOf<String>()
        when (language) {
            "فارسی" -> {
                for (i in 1..24) {
                    days.add(englishToPersianFormatter(i.toString()))
                }
            }
            else -> {
                for (i in 1..24) {
                    days.add(englishNumberFormatter(i.toString()))
                }
            }

        }
        return days.toTypedArray()
    }
}