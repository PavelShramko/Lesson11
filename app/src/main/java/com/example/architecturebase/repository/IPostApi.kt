package com.example.architecturebase.repository

import com.example.architecturebase.network.Post
import retrofit2.Call
import retrofit2.http.GET

interface IPostApi {
    @GET("/posts")
    fun getPosts(): Call<List<Post>>
}
