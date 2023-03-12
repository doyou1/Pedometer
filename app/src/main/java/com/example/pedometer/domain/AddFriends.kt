package com.example.pedometer.domain

data class AddFriends(
    val id: Int,
    val profileUrl: String,
    val name: String,
    // Add : 0
    // Pending : 1
    // Already : 2
    val status: Int,
    val steps: String,
    val communityId: String
)