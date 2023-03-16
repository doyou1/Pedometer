package com.example.pedometer.domain

data class WeekGoal(
    // 0 : over than goal
    // 1 : less then goal but not 0
    // 2 : 0 or no steps
    var status: Int, val textWeek: String)
