package com.example.bt05_ltdd.network

import com.example.bt05_ltdd.model.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface định nghĩa các API endpoints
 */
interface ApiService {
    
    /**
     * Lấy tất cả posts
     * GET https://jsonplaceholder.typicode.com/posts
     */
    @GET("posts")
    fun getAllPosts(): Call<List<Post>>
    
    /**
     * Lấy post theo ID
     * GET https://jsonplaceholder.typicode.com/posts/{id}
     */
    @GET("posts/{id}")
    fun getPostById(@Path("id") postId: Int): Call<Post>
    
    /**
     * Lấy posts theo userId
     * GET https://jsonplaceholder.typicode.com/posts?userId={userId}
     */
    @GET("posts")
    fun getPostsByUserId(@Query("userId") userId: Int): Call<List<Post>>
}

