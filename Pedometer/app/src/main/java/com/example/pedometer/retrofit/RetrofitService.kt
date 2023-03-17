package com.example.pedometer.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
    @POST("api/post")
    fun post(@Body list: List<Person>): Call<List<Person>>
}