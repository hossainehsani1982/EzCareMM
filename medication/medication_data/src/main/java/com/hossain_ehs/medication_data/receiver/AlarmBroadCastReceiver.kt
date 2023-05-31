package com.hossain_ehs.medication_data.receiver

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hossain_ehs.core.util.Constants.ACTION_START_SERVICE
import com.hossain_ehs.core.util.Constants.ACTION_UPDATE_SERVICE
import com.hossain_ehs.medication_data.service.AlarmService


class AlarmBroadCastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let { lContext ->
            intent?.let{lIntent ->

                var isServiceRunning = false
                val time = lIntent.getLongExtra("alarm_time", 0L)
                val userId = lIntent.getIntExtra("userId", 0)
                val medicationId = lIntent.getIntExtra("medicationId", 0)

                val am = lContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val runningProcesses = am.runningAppProcesses
                for (processInfo in runningProcesses) {
                    if (processInfo.importance == ActivityManager
                            .RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        val serviceName = processInfo.importanceReasonComponent?.className
                        isServiceRunning = serviceName != null &&
                                serviceName == AlarmService::class.java.name
                    }
                }

                if (!isServiceRunning) {
                    Intent(lContext, AlarmService::class.java).also {
                        it.putExtra("time", time)
                        it.putExtra("userId", userId)
                        it.putExtra("medicationId", medicationId)
                        it.action = ACTION_START_SERVICE
                        lContext.startService(it)
                    }
                } else {
                    Intent(lContext, AlarmService::class.java).also {
                        it.putExtra("time", time)
                        it.putExtra("userId", userId)
                        it.putExtra("medicationId", medicationId)
                        it.action = ACTION_UPDATE_SERVICE
                        lContext.startService(it)
                    }
                }
            }
        }

    }
}
