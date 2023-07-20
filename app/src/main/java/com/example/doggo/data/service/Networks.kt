package com.example.doggo.data.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://dog.ceo/"

const val TIME_OUT: Long = 120

private val okHttpClient = OkHttpClient.Builder()
    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
    .addInterceptor { chain ->
        val resp = chain.proceed(chain.request())
        // Deal with the response code
        if (resp.code == 200) {
            try {
                val myJson = resp.peekBody(2048).string() // peekBody() will not close the response
                println(myJson)
            } catch (e: Exception) {
                println("Error parse json from intercept..............")
            }
        } else {
            println(resp)
        }
        resp
    }
    .addInterceptor(HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }).build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(
        GsonConverterFactory.create()
    )
    .client(okHttpClient)
    .build()


val dogService: DogService = retrofit.create(DogService::class.java)