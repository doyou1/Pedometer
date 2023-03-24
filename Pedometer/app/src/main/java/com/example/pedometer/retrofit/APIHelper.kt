package com.example.pedometer.retrofit

import android.content.Context
import android.util.Log
import com.example.pedometer.activity.LoginActivity
import com.example.pedometer.room.Pedometer
import com.example.pedometer.room.RoomDBHelper
import com.example.pedometer.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.internal.synchronized
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.util.*
import kotlin.reflect.KFunction0

class APIHelper {
    companion object {
        private val TAG = this::class.java.simpleName
        private const val baseUrl = "http://192.168.0.16:8080/"
        fun processNewCommunityId(uuid: UUID, context: Context): String? {
            val length = 8
            var id = DataUtil.convertUUIDToString(uuid, length)
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(UserService::class.java)
            while (true) {
                val call = service.isDuplicateId(id)
                val isDuplicate = call.execute().body()
                if (isDuplicate != null) {
                    if (!isDuplicate) {
                        break
                    } else {
                        id = DataUtil.increaseHexString(id, length)
                    }
                } else {
                    return null
                }
            }
            return id
        }

        fun addItem(item: Pedometer, context: Context): Boolean? {
            val id = context.getSharedPreferences(TEXT_IS_LOGIN, Context.MODE_PRIVATE).getString(
                TEXT_LOGIN_ID, null
            )!!
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(UserService::class.java)
            val call = service.addItem(item, id)
            return call.execute().body()
        }

        fun updateItem(item: Pedometer, context: Context): Boolean? {
            val id = context.getSharedPreferences(TEXT_IS_LOGIN, Context.MODE_PRIVATE).getString(
                TEXT_LOGIN_ID, null
            )!!
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(UserService::class.java)
            val call = service.updateItem(item, id)
            return call.execute().body()
        }

        private fun saveCommunityId(id: String, context: Context) {
            val pref = context.getSharedPreferences(TEXT_COMMUNITY_ID, Context.MODE_PRIVATE)
            pref.edit().putString(TEXT_COMMUNITY_ID, id).apply()
        }

        fun isAbleLogin(
            id: String,
            pwd: String,
            showExistId: Boolean?
        ): Boolean? {
            // when new id login, reigster user
            // when exist id login, login user
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(UserService::class.java)
            val call = service.isAbleLogin(LoginUser(id, pwd, isNew = !showExistId!!))
            return call.execute().body()
        }

        fun processExistData(id: String): List<PedometerSteps>? {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(UserService::class.java)
            val call = service.getExistData(id)
            return call.execute().body()
        }
    }
}