package com.example.bt05_ltdd.model

import com.google.gson.annotations.SerializedName

/**
 * Data class đại diện cho một Post từ JSONPlaceholder API
 * API URL: https://jsonplaceholder.typicode.com/posts
 */
data class Post(
    @SerializedName("userId")
    val userId: Int,
    
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("body")
    val body: String
)

