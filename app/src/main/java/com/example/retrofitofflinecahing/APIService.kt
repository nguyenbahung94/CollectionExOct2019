package com.example.retrofitofflinecahing

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    companion object {
        const val BASE_URL = "https://api.chucknorris.io/jokes/"
    }

    @GET("{path}")
    fun getRandomJoke(@Path("path") path: String): Observable<Jokes>
 }