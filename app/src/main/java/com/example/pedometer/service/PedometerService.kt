package com.example.pedometer.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import com.example.pedometer.R
import com.example.pedometer.activity.MainActivity
import com.example.pedometer.receiver.ShutdownReceiver
import com.example.pedometer.room.DBHelper
import com.example.pedometer.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PedometerService : Service(), SensorEventListener {

    private val shutdownReceiver = ShutdownReceiver()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        reRegisterStepCounter()
        registerShutdownReceiver()

        if (applicationContext.getSharedPreferences(TEXT_NOTI_REPEAT, Context.MODE_PRIVATE)
                .getBoolean(
                    TEXT_ONOFF, true
                )
        ) {
            showPedometerNotification()
        }
        setRepeatAlarm()

        return START_STICKY
    }

    private fun reRegisterStepCounter() {
        val sm = getSystemService(SENSOR_SERVICE) as SensorManager

        try {
            sm.unregisterListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val sensor = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        // enable batching with delay of max 5 min
        sm.registerListener(
            this,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL,
            (5 * MICROSECONDS_IN_ONE_MINUTE).toInt()
        )
    }

    private fun registerShutdownReceiver() {
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SHUTDOWN);
        registerReceiver(shutdownReceiver, filter);
    }

    private fun showPedometerNotification() {
        val context = this
        GlobalScope.launch(Dispatchers.IO) {
            val notification = getNotification(context)
            GlobalScope.launch(Dispatchers.Main) {
                if (Build.VERSION.SDK_INT >= 26) {
                    startForeground(PEDOMETER_NOTIFICATION_ID, notification);
                } else {
                    (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(
                        PEDOMETER_NOTIFICATION_ID, notification
                    )
                }
            }
        }
    }

    private fun setRepeatAlarm() {
        val repeatTime =
            applicationContext.getSharedPreferences(TEXT_NOTI_REPEAT, Context.MODE_PRIVATE)
                .getInt(TEXT_VALUE, DEFAULT_VALUE_TEN_MINUTES)
        // default repeat time (after 10 minute)
        val nextTime = System.currentTimeMillis() + repeatTime
        val am = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getService(
            applicationContext,
            2,
            Intent(this, PedometerService::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        if (Build.VERSION.SDK_INT >= 23) {
            am.setAndAllowWhileIdle(AlarmManager.RTC, nextTime, pendingIntent)
        } else {
            am.set(AlarmManager.RTC, nextTime, pendingIntent)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let { e ->
            if (e.values[0] > Integer.MAX_VALUE || e.values[0].toInt() == 0) return
            DBHelper.process(this, e.values[0].toInt())
        }
    }

    private suspend fun getNotification(context: Context): Notification =
        withContext(Dispatchers.IO) {
            val notificationBuilder = if (Build.VERSION.SDK_INT >= 26) {
                val manager =
                    context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                val channel = NotificationChannel(
                    PEDOMETER_NOTIFICATION_CHANNEL_ID,
                    PEDOMETER_NOTIFICATION_CHANNEL_ID,
                    NotificationManager.IMPORTANCE_NONE
                )
                channel.importance = NotificationManager.IMPORTANCE_MIN; // ignored by Android O ...
                channel.enableLights(false);
                channel.enableVibration(false);
                channel.setBypassDnd(false);
                channel.setSound(null, null);
                manager.createNotificationChannel(channel);
                val builder = Notification.Builder(context, PEDOMETER_NOTIFICATION_CHANNEL_ID)
                builder
            } else {
                Notification.Builder(context)
            }
            val item = DBHelper.getCurrent(context)
            val contentText = if (item != null) {
                val steps = DBUtil.fromStepsJson(item.steps)
                var sum = 0
                for (step in steps) {
                    sum += step.steps
                }
                "${DateUtil.getFullToday()} ${DateUtil.getTime()} Steps: $sum"
            } else {
                "${DateUtil.getFullToday()} ${DateUtil.getTime()} Steps: 0"
            }
            notificationBuilder.setContentTitle(DateUtil.getFullToday()).setContentText(contentText)
            notificationBuilder.setPriority(Notification.PRIORITY_MIN)
                .setShowWhen(false)
                .setContentIntent(
                    PendingIntent.getActivity(
                        context,
                        0,
                        Intent(context, MainActivity::class.java),
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                    )
                )
                .setSmallIcon(R.drawable.ic_baseline_directions_run_24)
                .setOngoing(true)
            return@withContext notificationBuilder.build()
        }

    private fun stopStepCounter() {
        try {
            val sm = getSystemService(SENSOR_SERVICE) as SensorManager
            sm.unregisterListener(this)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopStepCounter()
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // won't happen
    }

}