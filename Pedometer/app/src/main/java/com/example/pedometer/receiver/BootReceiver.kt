package com.example.pedometer.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.pedometer.room.RoomDBHelper
import com.example.pedometer.room.Pedometer
import com.example.pedometer.room.dto.Steps
import com.example.pedometer.service.PedometerService
import com.example.pedometer.util.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            processReboot(it)
            if (Build.VERSION.SDK_INT >= 26) {
                it.startForegroundService(Intent(it, PedometerService::class.java))
            } else {
                it.startService(Intent(it, PedometerService::class.java))
            }
        }
    }

    /**
     * when device reboot
     */
    private fun processReboot(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val current = RoomDBHelper.getCurrent(context)
            // no data on reboot day
            if (current == null) {
                // insert initSteps: 0
                val pedometer = Pedometer(
                    0,
                    DateUtil.getCurrentTimestamp(),
                    DateUtil.getFullToday(),
                    0,
                    Gson().toJson(Steps(listOf()))
                )
                RoomDBHelper.insert(pedometer, context)
            } else {
                // 1. set current item's initSteps = 0
                current.initSteps = 0
                RoomDBHelper.update(current, context)
                GlobalScope.launch(Dispatchers.Main) {
                    val pref = context.getSharedPreferences(TEXT_REBOOT, Context.MODE_PRIVATE)
                    pref.edit()
                    // 2.1 save Reboot time
                    .putString(TEXT_REBOOT_TIME, "${DateUtil.getFullToday()} ${DateUtil.getTime()}")
                    // 2.2 save Reboot hour's before steps
                    .putInt(TEXT_REBOOT_BEFORE_STEPS, RoomDBUtil.computeSteps(current))
                    .apply()
                }
            }
        }
    }
}