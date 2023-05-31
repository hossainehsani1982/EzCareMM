package com.hossain_ehs.medication_domain.model.medication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class MedicineType(val type: String) : Parcelable {
    object Tablet: MedicineType("tablet")
    object Capsule: MedicineType("capsule")
    object Syrup: MedicineType("syrup")
    object Injection: MedicineType("injection")
    object Cream: MedicineType("cream")
    object Ointment: MedicineType("ointment")
    object Drops: MedicineType("drops")
    object Powder: MedicineType("powder")
    object Other: MedicineType("other")

    companion object {
        fun fromString(type: String): MedicineType {
            return when(type.lowercase()) {
                "tablet" -> Tablet
                "capsule" -> Capsule
                "syrup" -> Syrup
                "injection" -> Injection
                "cream" -> Cream
                "ointment" -> Ointment
                "drops" -> Drops
                "powder" -> Powder
                "other" -> Other
                else -> Tablet
            }
        }
    }
}
