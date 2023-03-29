package com.jh.backend.dto

import jakarta.persistence.*

data class Pedometer(
    val id: Long,
    val timestamp: Long,
    val yyyymmdd: String,
    var initSteps: Int,
    // 10분마다의 걸음 수 배열이 저장됨
    // "[{"0010": 10}, {"0020": 20}, {"0030": 30}, ....]"
    var steps: String
)