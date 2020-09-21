package com.fahmisbas.findmerecipe.data.httprequest

import com.fahmisbas.findmerecipe.data.Result
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    private val baseUrl = "https://api.spoonacular.com/"

    var txtQuery: String? = null

    private val apiService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    fun getResults(): Call<Result>? = txtQuery?.let {
        apiService.getRecipes(it)
    }
}