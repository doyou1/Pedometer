package com.example.pedometer.retrofit

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.util.*

class APIHelper {
    companion object {
        private val TAG = this::class.java.simpleName
        private const val baseUrl = "隠し"

        fun getNewCommunityId(uuid: UUID): String {
            val length = 8

            // UUID to byte array
            val uuidBytes = ByteBuffer.wrap(ByteArray(16)).apply {
                putLong(uuid.mostSignificantBits)
                putLong(uuid.leastSignificantBits)
            }.array()

            // use hash function for sha-256
            val md = MessageDigest.getInstance("SHA-256")
            val hashBytes = md.digest(uuidBytes)

            // hash value to hex value
            val hashBigInt = BigInteger(1, hashBytes)
            var hashStr = hashBigInt.toString(16)

            // hex value to string (substring)
            val resultStr = StringBuilder()
            val step = hashStr.length / length
            for (i in 0 until length) {
                resultStr.append(hashStr[i * step])
            }
            Log.e(TAG, "result: $resultStr")

            // check value duplicate
//            while(true) {
//                if(!isDuplicateCommunityId(resultStr.toString())) break
            // randomShuffle
//            hashStr = randomShuffle(hashStr)
            //   }

            return "#$resultStr"
        }

        private fun isDuplicateCommunityId(id: String): Boolean {
            return false
        }


        fun isAbleLogin(id: String, pwd: String, context: Context): Boolean {
            // when new id login, reigster user
            // when exist id login, login user
//            loginProcess(id, pwd)
            val result = true
            return result
        }

        fun post() {

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(RetrofitService::class.java)

            val list = listOf(
                Person(1, "name1", 1),
                Person(2, "name2", 2),
                Person(3, "name3", 3),
                Person(4, "name4", 4),
                Person(5, "name5", 5),
                Person(6, "name6", 6),
                Person(7, "name7", 7),
            )

            val call: Call<List<Person>> = service.post(list)
            call.enqueue(object : Callback<List<Person>> {
                override fun onResponse(
                    call: Call<List<Person>>,
                    response: Response<List<Person>>
                ) {
                    Log.e(TAG, response.body().toString())
                }
                override fun onFailure(call: Call<List<Person>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }


    }

}