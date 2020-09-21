package com.fahmisbas.findmerecipe.ui.activities.recipelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fahmisbas.findmerecipe.data.Data
import com.fahmisbas.findmerecipe.data.Result
import com.fahmisbas.findmerecipe.data.httprequest.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeListViewModel : ViewModel(), OnQueryChangeListener {

    private val apiService = ApiService()

    private var _error = MutableLiveData<Boolean>()
    private var _recipes = MutableLiveData<List<Data>>()

    val error: LiveData<Boolean> = _error
    val recipes: LiveData<List<Data>> = _recipes

    init {
        loadData()
    }

    private fun loadData() {
        apiService.getResults()?.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                if (response.isSuccessful) {
                    _recipes.postValue(response.body()?.recipeData)
                    _error.postValue(false)
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                _error.postValue(true)
            }
        })
    }

    override fun onQueryChanged(txt: String) {
        apiService.txtQuery = txt
        loadData()
    }
}