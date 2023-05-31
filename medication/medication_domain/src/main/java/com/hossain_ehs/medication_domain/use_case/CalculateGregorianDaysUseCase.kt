package com.hossain_ehs.medication_domain.use_case

class CalculateGregorianDaysUseCase {

    operator fun  invoke(month: Int, isLeapYear: Boolean): Int {
        when (month - 1) {
            0 -> return 31
            1 -> {
                return when (isLeapYear) {
                    true -> 29
                    false -> 28
                }
            }
            2 -> return 31
            3 -> return 30
            4 -> return 31
            5 -> return 30
            6 -> return 31
            7 -> return 31
            8 -> return 30
            9 -> return 31
            10 -> return 30
            11 -> return 31
        }
        return 0
    }
}