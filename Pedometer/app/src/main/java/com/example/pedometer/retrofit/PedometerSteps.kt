package com.example.pedometer.retrofit

data class PedometerSteps(
    val _id: Long,
    val userId: String,
    val timestamp: Long,
    val yyyymmdd: String,
    val initSteps: Int,
    val steps: String
)
