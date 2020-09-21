package com.fahmisbas.findmerecipe.data.httprequest

import com.fahmisbas.findmerecipe.data.Result
import com.fahmisbas.findmerecipe.data.Result.Companion.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("recipes/search?&number=100&apiKey=$API_KEY")
    fun getRecipes(@Query("query") query: String): Call<Result>
}