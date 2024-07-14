package com.example.myapplication.models.profile

data class RequestToken(
    val expires_at: String,
    val request_token: String,
    val success: Boolean
)