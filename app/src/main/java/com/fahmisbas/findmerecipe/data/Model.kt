package com.fahmisbas.findmerecipe.data

import com.google.gson.annotations.SerializedName

class Result(
    @SerializedName("results")
    val recipeData: List<Data>
) {
    companion object {
        const val API_KEY = "YOUR_API_KEY"
    }
}

class Data(
    @SerializedName("title")
    val name: String,
    @SerializedName("readyInMinutes")
    val readyInMinutes: String,
    @SerializedName("servings")
    val servings: String,
    @SerializedName("image")
    val imageName: String,
    @SerializedName("sourceUrl")
    val sourceUrl: String
)