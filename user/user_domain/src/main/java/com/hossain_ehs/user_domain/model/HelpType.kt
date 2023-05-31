package com.hossain_ehs.user_domain.model


sealed class HelpType(val type: String){
    object SelfHelper: HelpType("self_helper")
    object HelpGiver: HelpType("help_giver")
    object HelpTaker: HelpType("help_taker")

    companion object {
        fun fromString(type: String): HelpType {
            return when(type.lowercase()) {
                "self_helper" -> SelfHelper
                "help_giver" -> HelpGiver
                "help_taker" -> HelpTaker
                else -> SelfHelper
            }
        }
    }

}
