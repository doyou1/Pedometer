package com.example.pedometer.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("api/isDuplicateId")
    fun isDuplicateId(@Body value: String): Call<Boolean>

    @POST("api/isAbleLogin")
    fun isAbleLogin(@Body value: LoginUser): Call<Boolean>

    @POST("api/getExistData")
    fun getExistData(@Body value: String): Call<List<PedometerSteps>>
}