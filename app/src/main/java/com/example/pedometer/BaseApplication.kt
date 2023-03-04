package com.example.pedometer

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.pedometer.util.*

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val initPref = getSharedPreferences(TEXT_INIT, Context.MODE_PRIVATE)
        val isInit = initPref.getBoolean(TEXT_INIT, false)
        if (!isInit) {
            val notiRepeatPref = getSharedPreferences(TEXT_NOTI_REPEAT, Context.MODE_PRIVATE)
            notiRepeatPref.edit().putInt(TEXT_VALUE, DEFAULT_VALUE_TEN_MINUTES).apply()
            notiRepeatPref.edit().putBoolean(TEXT_ONOFF, true).apply()
            initPref.edit().putBoolean(TEXT_INIT, true).apply()
        }
    }
}