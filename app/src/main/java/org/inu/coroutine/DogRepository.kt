package org.inu.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class DogRepository{
    suspend fun getDogRequest():Result<GetData>{
        val getDog = Retrofit.service.getDogFacts()
        if (getDog.isSuccessful) {
            return withContext(Dispatchers.IO) {
                Result.Success(getDog.body()!!)
            }
        }
        return withContext(Dispatchers.IO){
            Result.Error(Exception("Cannot open HttpURLConnection"))
        }
    }
}