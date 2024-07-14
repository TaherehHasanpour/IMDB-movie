package com.example.myapplication.models.home

data class AddToWatchListRequest(
    val media_id: Int,
    val media_type: String,
    val watchlist: Boolean
)