package com.example.pedometer.retrofit

import com.example.pedometer.room.Pedometer
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @POST("api/isDuplicateId")
    fun isDuplicateId(@Body value: String): Call<Boolean>

    @POST("api/isAbleLogin")
    fun isAbleLogin(@Body value: LoginUser): Call<Boolean>

    @POST("api/getExistData")
    fun getExistData(@Body value: String): Call<List<PedometerSteps>>

    @POST("api/addItem/[id}")
    fun addItem(@Body value: Pedometer, @Path("id") id: String): Call<Boolean>

    @POST("api/updateItem/{id}")
    fun updateItem(@Body value: Pedometer, @Path("id") id: String): Call<Boolean>
}