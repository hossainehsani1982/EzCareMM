package com.hossain_ehs.core.util

import android.content.Context

sealed class UiText{
    data class DynamicString(val text : String) : UiText()
    data class ResourceString(var resId : Int) : UiText()

    fun asString(context : Context) : String{
        return when(this){
            is DynamicString -> text
            is ResourceString -> context.getString(resId)
        }
    }
}
