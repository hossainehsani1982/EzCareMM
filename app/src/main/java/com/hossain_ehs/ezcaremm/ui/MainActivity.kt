package com.hossain_ehs.ezcaremm.ui

import android.Manifest
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hossain_ehs.core.util.Constants.REQUEST_CODE_SET_ALARM
import com.hossain_ehs.ezcaremm.databinding.ActivityMainBinding
import com.hossain_ehs.medication_data.receiver.AlarmBroadCastReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(isPermissionGranted()) {

            binding = ActivityMainBinding.inflate(layoutInflater)
            val view = binding.root
            setContentView(view)

        }
    }

    private fun isPermissionGranted(): Boolean {
        return if (checkSelfPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
            == PackageManager.PERMISSION_GRANTED
            &&
            checkSelfPermission(Manifest.permission.FOREGROUND_SERVICE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SCHEDULE_EXACT_ALARM),
                1
            )
            false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_SET_ALARM -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    // permission granted, proceed with your logic
                } else {
                    // permission denied, show a message to the user
                }
            }
        }
    }
//    private fun checkService(){
//        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        val runningProcesses = am.runningAppProcesses
//
//        for (processInfo in runningProcesses) {
//            if (processInfo.importance ==
//                ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//                val serviceName = processInfo.importanceReasonComponent?.className
//
//                if (serviceName != null && serviceName == MyService::class.java.name) {
//                    // MyService is running in the foreground
//                    break
//                }
//            }
//        }
//    }

}