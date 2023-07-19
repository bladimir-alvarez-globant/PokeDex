package com.bladoae.pokedex.requestmanager.requestmanager

import com.bladoae.pokedex.requestmanager.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator(
    private val baseUrl: String
) {

    private lateinit var retrofit: Retrofit

    init {
        setupRetrofit()
    }

    private fun setupRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}