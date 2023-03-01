package com.example.pedometer.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.pedometer.service.PedometerService

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(Build.VERSION.SDK_INT >= 26) {
            context?.startForegroundService(Intent(context, PedometerService::class.java))
        } else {
            context?.startService(Intent(context, PedometerService::class.java))
        }
    }
}