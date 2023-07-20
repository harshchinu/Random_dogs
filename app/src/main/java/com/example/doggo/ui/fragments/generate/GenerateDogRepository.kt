package com.example.doggo.ui.fragments.generate

import com.example.doggo.data.model.DogResponse
import com.example.doggo.data.model.ResponseWrapper
import com.example.doggo.data.service.DogService

class GenerateDogRepository(private val dogService: DogService) {

    suspend fun getRandomDog(): ResponseWrapper<DogResponse> {
        return try {
            ResponseWrapper.success(dogService.getDog())
        } catch (e: Exception) {
            ResponseWrapper.error(error = e.message)
        }
    }
}