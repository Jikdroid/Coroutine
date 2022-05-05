package org.inu.coroutine

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://dog-api.kinduff.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: HttpService = retrofit.create(HttpService::class.java)
}