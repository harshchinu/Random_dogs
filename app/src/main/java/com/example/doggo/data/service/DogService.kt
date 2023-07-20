package com.example.doggo.data.service

import com.example.doggo.data.model.DogResponse
import retrofit2.http.GET

interface DogService {

    @GET("api/breeds/image/random")
    suspend fun getDog(): DogResponse
}