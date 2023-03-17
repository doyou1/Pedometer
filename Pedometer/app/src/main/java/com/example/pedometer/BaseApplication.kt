package com.example.pedometer

import android.app.Application
import android.content.Context
import com.example.pedometer.retrofit.APIHelper
import com.example.pedometer.util.*

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        val initPref = getSharedPreferences(TEXT_INIT, Context.MODE_PRIVATE)
        val isInit = initPref.getBoolean(TEXT_INIT, false)
        if (!isInit) {
            val notiRepeatPref = getSharedPreferences(TEXT_NOTI_REPEAT, Context.MODE_PRIVATE)
            notiRepeatPref.edit().putInt(TEXT_VALUE, DEFAULT_VALUE_TEN_MINUTES).apply()
            notiRepeatPref.edit().putBoolean(TEXT_ONOFF, true).apply()
            initPref.edit().putBoolean(TEXT_INIT, true).apply()
        }
    }

    fun processCommunityId() {
        val pref = getSharedPreferences(TEXT_COMMUNITY_ID, Context.MODE_PRIVATE)
        if (pref.getString(TEXT_COMMUNITY_ID, null) == null) {
            val communityId = APIHelper.getNewCommunityId(DataUtil.getDeviceUUID(this))
            pref.edit().putString(TEXT_COMMUNITY_ID, communityId).apply()
        }
    }
}