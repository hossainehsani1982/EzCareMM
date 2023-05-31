package com.hossain_ehs.core_ui.util

import android.app.Activity

object Constants {

    const val ACTION_START_RESUME_SERVICE = "ACTION_START_RESUME_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    const val ACTION_SHOW_ALARM_FRAGMENT = "ACTION_SHOW_ALARM_FRAGMENT"

    const val NOTIFICATION_CHANNEL_ID = "medication_alert_channel"
    const val NOTIFICATION_CHANNEL_NAME = "medication_alert"
    const val NOTIFICATION_ID = 1

    const val ADD_USER_RESULT_OK = Activity.RESULT_FIRST_USER
    const val EDIT_USER_RESULT_OK = Activity.RESULT_FIRST_USER + 1
    const val ADD_MEDICINE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
    const val EDIT_MEDICINE_RESULT_OK = Activity.RESULT_FIRST_USER + 3

}