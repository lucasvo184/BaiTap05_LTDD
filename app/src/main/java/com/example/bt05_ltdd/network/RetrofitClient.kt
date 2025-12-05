package com.example.bt05_ltdd.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object để quản lý Retrofit instance
 */
object RetrofitClient {
    
    // Base URL của JSONPlaceholder API
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    
    // Lazy initialization của Retrofit instance
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    // Lazy initialization của ApiService
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

