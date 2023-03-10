package com.example.pedometer.util

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {

        fun getFullToday(): String {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            return sdf.format(cal.time)
        }

        fun getToday(): String {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("MM/dd")
            return sdf.format(cal.time)
        }

        fun getTime(): String {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("HH:mm:ss")
            return sdf.format(cal.time)
        }

        fun getOneMonthAgoDate(): Long {
            val c = Calendar.getInstance()
            c.timeInMillis = System.currentTimeMillis();
            c.add(Calendar.MONTH, -1)
            c.set(Calendar.HOUR_OF_DAY, 0)
            c.set(Calendar.MINUTE, 0)
            c.set(Calendar.SECOND, 0)
            c.set(Calendar.MILLISECOND, 0)
            return c.timeInMillis
        }

        fun getThreeMonthAgoDate(): Long {
            val c = Calendar.getInstance()
            c.timeInMillis = System.currentTimeMillis();
            c.add(Calendar.MONTH, -1)
            c.set(Calendar.DAY_OF_WEEK, 2)    // monday
            c.set(Calendar.HOUR_OF_DAY, 0)
            c.set(Calendar.MINUTE, 0)
            c.set(Calendar.SECOND, 0)
            c.set(Calendar.MILLISECOND, 0)
            return c.timeInMillis
        }

        fun getCurrentTimestamp(): Long {
            val c = Calendar.getInstance()
            c.timeInMillis = System.currentTimeMillis();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            return c.timeInMillis;
        }

        fun getCurrentHour(): String {
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("HH")
            return sdf.format(c.time)
        }

        fun convertDate(time: Long): String {
            val cal = Calendar.getInstance()
            cal.timeInMillis = time
            val sdf = SimpleDateFormat("yyyyMMdd")
            return sdf.format(cal.time)
        }

        fun isRebootToday(context: Context): Boolean {
            val pref = context.getSharedPreferences(TEXT_REBOOT, Context.MODE_PRIVATE)
            val rebootTime = pref.getString(TEXT_REBOOT_TIME, null)
            return if (rebootTime == null) false
            else {
                val c = convertStringToCalendar(rebootTime, "yyyy/MM/dd HH:mm:ss")
                val sdf = SimpleDateFormat("yyyy/MM/dd")
                val format = sdf.format(c.time)
                format == getToday()
            }
        }

        fun getRebootHour(context: Context) : String {
            val pref = context.getSharedPreferences(TEXT_REBOOT, Context.MODE_PRIVATE)
            val rebootTime = pref.getString(TEXT_REBOOT_TIME, null)
            val c = convertStringToCalendar(rebootTime!!, "yyyy/MM/dd HH:mm:ss")
            val sdf = SimpleDateFormat("HH")
            return sdf.format(c.time)
        }

        fun convertStringToCalendar(rebootTime: String, format: String): Calendar {
            val sdf = SimpleDateFormat(format)
            val c = Calendar.getInstance()
            c.time = sdf.parse(rebootTime)
            return c
        }

    }
}