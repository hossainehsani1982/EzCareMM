package com.hossain_ehs.medication_domain.use_case

class EnglishNumberFormatterUseCase {
    operator fun invoke(englishStr: String): String {
        var result = ""
        var en: Char
        for (ch in englishStr) {
            en = ch
            when (ch) {
                '0' -> en = '0'
                '1' -> en = '1'
                '2' -> en = '2'
                '3' -> en = '3'
                '4' -> en = '4'
                '5' -> en = '5'
                '6' -> en = '6'
                '7' -> en = '7'
                '8' -> en = '8'
                '9' -> en = '9'
            }
            result = "${result}$en"
        }
        if (result.length < 2) {
            result = "0$result"
        }
        return result
    }
}