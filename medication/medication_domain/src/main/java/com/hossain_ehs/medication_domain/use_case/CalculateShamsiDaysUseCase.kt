package com.hossain_ehs.medication_domain.use_case

class CalculateShamsiDaysUseCase {
    operator fun invoke(month: Int, isLeap: Boolean): Int {
        when (month) {
            in 0..6 -> return 31
            in 7..10 -> return 30
            11 -> {
                return when (isLeap) {
                    true -> 30
                    false -> 29
                }
            }
        }
        return 0
    }
}