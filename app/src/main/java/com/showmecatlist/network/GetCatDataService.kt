package com.showmecatlist.network

import com.showmecatlist.dataclasses.Person
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GetCatDataService {

    @GET("people.json")
    fun getData(): Call<List<Person>>

    /**
     * Companion object to create the DataService
     */
    companion object Factory {
        fun create(): GetCatDataService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://agl-developer-test.azurewebsites.net/")
                .build()

            return retrofit.create(GetCatDataService::class.java)
        }
    }
}