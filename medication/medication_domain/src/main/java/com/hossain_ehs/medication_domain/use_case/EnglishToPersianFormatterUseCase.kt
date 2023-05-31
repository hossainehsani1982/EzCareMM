package com.hossain_ehs.medication_domain.use_case

class EnglishToPersianFormatterUseCase {
    operator fun invoke(englishString : String) : String {
        var result = ""
        var en: Char
        for (ch in englishString) {
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
            }
            result = "${result}$en"
        }
        if (result.length < 2) {
            result = "۰$result"
        }
        return result
    }
}