package com.hossain_ehs.medication_data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.hossain_ehs.medication_data.receiver.AlarmBroadCastReceiver
import com.hossain_ehs.medication_domain.model.alarm.AlarmItem
import com.hossain_ehs.medication_domain.model.alarm.AlarmScheduler
import java.time.ZoneId


class AlamSchedulerImpl(
    private val context: Context,
) : AlarmScheduler {


    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    private var userId = 0
    private var medicationId = 0

    override fun setUserID(userId: Int) {
        this.userId = userId
    }

    override fun setMedicationId(medicationId: Int) {
        this.medicationId = medicationId
    }


    override fun schedule(item: AlarmItem) {

        println(item.time)

        val intent = Intent(context, AlarmBroadCastReceiver::class.java)
        //item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000
        intent.putExtra(
            "alarm_time",
            200000
        )
        intent.putExtra("medication_id", medicationId)
        intent.putExtra("user_id", userId)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            20000,
            PendingIntent.getBroadcast(
                context,
                item.id.hashCode(),
                intent,
                PendingIntent.FLAG_IMMUTABLE
                        or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

    }

    override fun cancel(item: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                Intent(context, AlarmBroadCastReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE
                        or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
    }
}