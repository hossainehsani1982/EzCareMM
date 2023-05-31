package com.hossain_ehs.medication_data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.hossain_ehs.core.domain.shared_preferences.Preferences
import com.hossain_ehs.core.util.Constants.ACTION_START_SERVICE
import com.hossain_ehs.core.util.Constants.ACTION_STOP_SERVICE
import com.hossain_ehs.core.util.Constants.ACTION_UPDATE_SERVICE
import com.hossain_ehs.core.util.Constants.NOTIFICATION_CHANNEL_ID
import com.hossain_ehs.core.util.Constants.NOTIFICATION_CHANNEL_NAME
import com.hossain_ehs.core.util.Constants.NOTIFICATION_ID
import com.hossain_ehs.medication_data.R
import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.medication_domain.use_case.MedicationUseCases
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import java.util.*
import java.util.stream.Collectors
import javax.inject.Inject


@AndroidEntryPoint
class AlarmService : LifecycleService() {
    private lateinit var remoteViews: RemoteViews
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var txtMedicationName: String
    private lateinit var txtNextSchedule: String
    private lateinit var txtUserName: String
    private var isFirstTime = false

    @Inject
    lateinit var medicationUseCases: MedicationUseCases

    @Inject
    lateinit var preferences: Preferences
    private var serviceJob = Job()
    private var serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { mIntent ->
            when (mIntent.action) {
                ACTION_START_SERVICE -> {
                    CoroutineScope(Dispatchers.Default).launch {
                        preferences.setServerStatus(true)
                    }
                    isFirstTime = true
                    getMedicationById()

                }

                ACTION_UPDATE_SERVICE -> {

                }

                ACTION_STOP_SERVICE -> {
                    CoroutineScope(Dispatchers.Default).launch {
                        preferences.setServerStatus(false)
                    }
                }

                else -> Unit
            }
        }
        serviceScope.launch {
            preferences.getServerStatus().collect {
                flowOf(it) // Replace with your actual flow source
                    .distinctUntilChanged()
                    .collect { shouldStop ->
                        if (!shouldStop) {
                            stopSelf()
                        }
                    }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun getMedicationById() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                val medications = medicationUseCases.getMedicationsOrdByScheduleACSUseCase()
                setStrings(medications.stream()
                    .filter { m ->
                        m.nextSchedule
                        -
                        medications.first().nextSchedule <= 120000
                    }
                    .collect(Collectors.toList())
                )
            }
            createNotification()
            setRemoteViews()
            getAlarmActivityPendingIntent()
        }
    }

    private fun setStrings(medList: List<Medication>) {
        val currentMedication = medList.first()
        txtMedicationName = currentMedication.medicineName
        txtUserName = currentMedication.userName
        var multipleUsers = false
        if (medList.size > 1) {
            for (med in medList) {
                txtMedicationName = txtMedicationName + ", " + med.medicineName
                if (med.userName != txtUserName && !multipleUsers) {
                    multipleUsers = true
                    txtUserName = "$txtUserName ..."
                }
            }
        }
        txtNextSchedule = medicationUseCases
            .dateToStringUseCase(
                language = Locale.getDefault().displayLanguage,
                timeInMillis = currentMedication.nextSchedule,
                months = this@AlarmService
                    .resources
                    .getStringArray(
                        com.hossain_ehs.core.R.array.months
                    )
            )
    }


    private fun createNotification() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        createNotificationChannel(notificationManager)
        remoteViews = RemoteViews(packageName, R.layout.notification_layout)

        notificationBuilder = if (android.os.Build.VERSION.SDK_INT >=
            android.os.Build.VERSION_CODES.S
        ) {
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(false)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_notif)
                .setContentTitle("EzCare")
                .setCustomBigContentView(remoteViews)
        } else
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(false)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_notif)
                .setContentTitle("EzCare")
                .setCustomContentView(remoteViews)


        startForeground(NOTIFICATION_ID, notificationBuilder.build()).also {
            Service.START_STICKY
        }
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }


    private fun setRemoteViews() {


        remoteViews.setTextViewText(R.id.txtPatientName, txtUserName)
        remoteViews.setTextViewText(R.id.txtMedicineName, txtMedicationName)
        remoteViews.setTextViewText(R.id.txtNextSchedule, txtNextSchedule)
//        remoteViews.setTextViewText(R.id.txtRemainingTime, "in $timeToAlarm")
        notificationManager.notify(1, notificationBuilder.build())
    }

    private fun getAlarmActivityPendingIntent() {
        val deeplinkString = this.getString(com.hossain_ehs.core.R.string.deep_link_to_alarm2)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deeplinkString))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        this.startActivity(intent)

    }

    override fun onDestroy() {
        println("destroy")
        super.onDestroy()
        serviceJob.cancel()
    }
}
