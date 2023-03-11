package com.example.pedometer.domain

data class CommunityNotifications(
    val id: Int,
    val isNew: Boolean,
    val profileUrl: String,
    val name: String,
    val content: String,
    val date: String
)
