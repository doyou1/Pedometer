package com.example.pedometer.api

import android.content.Context
import android.util.Log
import java.math.BigInteger
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.util.*

class APIHelper {
    companion object {
        private val TAG = this::class.java.simpleName
        fun getNewCommunityId(uuid: UUID) : String {
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

        private fun isDuplicateCommunityId(id: String) : Boolean {
            return false
        }
    }

}