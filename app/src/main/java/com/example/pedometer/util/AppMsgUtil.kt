package com.example.pedometer.util

import android.app.Activity
import com.devspark.appmsg.AppMsg

class AppMsgUtil {

    companion object {
        fun showInfoMsg(text: String, activity: Activity) {
            val appMsg =
                AppMsg.makeText(activity, text, AppMsg.STYLE_INFO)
            appMsg.duration = DURATION_APP_MSG
            appMsg.show()
        }

        fun showInfoMsg(text: String, status: Int, activity: Activity) {
            val appMsg =
                AppMsg.makeText(activity, "$text status: $status", AppMsg.STYLE_INFO)
            appMsg.duration = DURATION_APP_MSG
            appMsg.show()
        }

        fun showErrorMsg(text: String, status: Int, activity: Activity) {
            val appMsg =
                AppMsg.makeText(activity, "$text status: $status", AppMsg.STYLE_INFO)
            appMsg.duration = DURATION_APP_MSG
            appMsg.show()
        }
    }
}