package com.hossain_ehs.medication_domain.use_case


class GetMinutesUseCase (
    private val englishToPersianFormatter: EnglishToPersianFormatterUseCase,
    private val englishNumberFormatter: EnglishNumberFormatterUseCase,

        ) {
    operator fun invoke(
        language : String)
    : Array<String> {
        val minutes = mutableListOf<String>()

        when (language) {
            "فارسی" -> {
                for (i in 0..59) {
                    minutes.add(englishToPersianFormatter(i.toString()))
                }
            }
            else -> {
                for (i in 0..59) {
                    minutes.add(englishNumberFormatter(i.toString()))
                }
            }

        }
        return minutes.toTypedArray()
    }

}