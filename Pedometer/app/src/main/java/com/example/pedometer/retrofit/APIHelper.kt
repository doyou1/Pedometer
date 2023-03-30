package com.example.pedometer.retrofit

import android.content.Context
import android.util.Log
import com.example.pedometer.room.Pedometer
import com.example.pedometer.util.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class APIHelper {
    companion object {
        private val TAG = this::class.java.simpleName
        private const val baseUrl = "http://52.196.227.48:8080/"
        fun processNewCommunityId(uuid: UUID, context: Context): String? {
            val length = 8
            var id = DataUtil.convertUUIDToString(uuid, length)
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(UserService::class.java)
            while (true) {
                try {
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
                } catch (e: Exception) {
                    Log.e(TAG, "error in service.isDuplicateId(id)")
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

            return try {
                Log.e(TAG, "error in service.addItem(item, id)")
                call.execute().body()
            } catch (e: Exception) {
                false
            }
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
            return try {
                Log.e(TAG, "error in service.updateItem(item, id)")
                call.execute().body()
            } catch (e: Exception) {
                false
            }
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
            return try {
                Log.e(
                    TAG,
                    "error in service.isAbleLogin(LoginUser(id, pwd, isNew = !showExistId!!))"
                )
                call.execute().body()
            } catch (e: Exception) {
                false
            }
        }

        fun processExistData(id: String): List<PedometerSteps>? {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(UserService::class.java)
            val call = service.getExistData(id)
            return try {
                call.execute().body()
            } catch (e: Exception) {
                Log.e(TAG, "error in service.getExistData(id)")
                null
            }
        }
    }
}