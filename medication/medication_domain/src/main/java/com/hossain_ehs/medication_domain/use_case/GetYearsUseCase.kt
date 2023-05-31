package com.hossain_ehs.medication_domain.use_case

class GetYearsUseCase (
    private val englishToPersianFormatter: EnglishToPersianFormatterUseCase
        ) {
    operator fun invoke(language : String) : Array<String> {
       val years :Array<String> = arrayOf()
        val list: MutableList<String> = years.toMutableList()
        when (language) {
            "فارسی" -> {
                for (i in 1390..1499) {
                    list.add(englishToPersianFormatter(i.toString()))
                }
            }
            else -> {
                for (i in 2010..2099) {

                    list.add(i.toString())
                }
            }

        }
        return list.toTypedArray()
    }
}