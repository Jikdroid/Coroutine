package org.inu.coroutine

import retrofit2.Response
import retrofit2.http.GET

interface HttpService {
    @GET("/api/fats")
    suspend fun getDogFacts()
    : Response<GetData>
}