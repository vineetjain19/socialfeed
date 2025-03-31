package com.vineet.socialmedia

import retrofit2.http.GET

interface ApiService {
    @GET("photos")
    suspend fun getPosts(): List<Post>
}