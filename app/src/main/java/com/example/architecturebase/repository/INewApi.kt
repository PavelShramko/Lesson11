package com.example.architecturebase.repository

import com.example.architecturebase.network.NewPost
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface INewApi {
    @GET("/post")
    @Headers(
        "x-rapidapi-key: aa51b0e2ccd449c4563669e2953f2968",
        "x-rapidapi-host: api.openweathermap.org"
    )

    fun getNewPost(): Single<List<NewPost>>
}