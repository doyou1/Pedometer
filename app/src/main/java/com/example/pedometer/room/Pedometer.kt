package com.example.pedometer.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pedometer(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: Long,
    val initSteps: Int,
    // 10분마다의 걸음 수 배열이 저장됨
    // "[{"0010": 10}, {"0020": 20}, {"0030": 30}, ....]"
    var steps: String
)