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
        fun processNewCommunityId(uuid: UUID, context: Context) {
            val length = 8
            var result = DataUtil.convertUUIDToString(uuid, length)
            checkCommunityId(result, length, context)
        }

        fun addItem(item: Pedometer, context: Context) {
            val id = context.getSharedPreferences(TEXT_IS_LOGIN, Context.MODE_PRIVATE).getString(
                TEXT_LOGIN_ID, null
            )!!
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(UserService::class.java)
            val call = service.addItem(item, id)
            call.enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    response.body()?.let { isSuccess ->
                        Log.d(TAG, "addItem isSuccess: $isSuccess")
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

        fun updateItem(item: Pedometer, context: Context) {
            val id = context.getSharedPreferences(TEXT_IS_LOGIN, Context.MODE_PRIVATE).getString(
                TEXT_LOGIN_ID, null
            )!!

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(UserService::class.java)
            val call = service.updateItem(item, id)
            call.enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    response.body()?.let { isSuccess ->
                        Log.d(TAG, "updateItem isSuccess: $isSuccess")
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

        private fun checkCommunityId(id: String, length: Int, context: Context) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(UserService::class.java)
            val call = service.isDuplicateId(id)
            call.enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    response.body()?.let { isDuplicate ->
                        if (!isDuplicate) {
                            saveCommunityId(id, context)
                        } else {
                            checkCommunityId(
                                DataUtil.increaseHexString(id, length),
                                length,
                                context
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }

        private fun saveCommunityId(id: String, context: Context) {
            val pref = context.getSharedPreferences(TEXT_COMMUNITY_ID, Context.MODE_PRIVATE)
            pref.edit().putString(TEXT_COMMUNITY_ID, id).apply()
        }

        fun isAbleLogin(
            id: String,
            pwd: String,
            showExistId: Boolean?,
            successLogin: (String) -> Unit,
            failLogin: () -> Unit
        ) {
            // when new id login, reigster user
            // when exist id login, login user
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(UserService::class.java)
            val call = service.isAbleLogin(LoginUser(id, pwd, isNew = !showExistId!!))
            call.enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    response.body()?.let { isAbleLogin ->
                        if (isAbleLogin) successLogin(id)
                        else failLogin()
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

        fun processExistData(
            id: String, context: Context, goToMainActivity: () -> Unit
        ) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(UserService::class.java)
            val call = service.getExistData(id)
            call.enqueue(object : Callback<List<PedometerSteps>> {
                override fun onResponse(
                    call: Call<List<PedometerSteps>>,
                    response: Response<List<PedometerSteps>>
                ) {
                    response.body()?.let {
                        Log.e(TAG, "it: $it")
                        GlobalScope.launch(Dispatchers.IO) {
                            RoomDBHelper.replaceUserData(it, context)
                            context.getSharedPreferences(TEXT_REPLACE_USER_DATA, Context.MODE_PRIVATE).edit().putBoolean(
                                DateUtil.getFullToday(), true).apply()

                            GlobalScope.launch(Dispatchers.Main) {
                                goToMainActivity()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<PedometerSteps>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

}