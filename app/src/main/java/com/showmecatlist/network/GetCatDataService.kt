package com.showmecatlist.network

import com.showmecatlist.dataclasses.Person
import com.showmecatlist.utils.BASE_URL
import com.showmecatlist.utils.URL_ENDPOINT_PERSON
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GetCatDataService {

    @GET(URL_ENDPOINT_PERSON)
    fun getData(): Call<List<Person>>

    /**
     * Companion object to create the DataService
     */
    companion object Factory {
        fun create(): GetCatDataService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(GetCatDataService::class.java)
        }
    }
}