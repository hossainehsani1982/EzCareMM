package com.hossain_ehs.medication_domain.use_case

class SetIntervalValueUseCase {
    operator fun invoke(intervalUnit: Int) : Int {
            when (intervalUnit) {
                0 -> return 60
                1 -> return 24
                2 -> return 31
                3 -> return 12
                4 -> return 120
            }
            return 0
        }
    }
