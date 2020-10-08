package com.fahmisbas.findmerecipe.ui.activities.recipelist

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fahmisbas.findmerecipe.R
import com.fahmisbas.findmerecipe.data.Data
import com.fahmisbas.findmerecipe.ui.activities.recipedetail.RecipeWebViewActivity
import com.fahmisbas.findmerecipe.ui.activities.recipedetail.RecipeWebViewActivity.Companion.EXTRA_RECIPE_NAME
import com.fahmisbas.findmerecipe.ui.activities.recipedetail.RecipeWebViewActivity.Companion.EXTRA_RECIPE_URL
import com.fahmisbas.findmerecipe.ui.adapter.RecipeListAdapter
import com.fahmisbas.findmerecipe.utils.gone
import com.fahmisbas.findmerecipe.utils.makeToast
import com.fahmisbas.findmerecipe.utils.visible
import kotlinx.android.synthetic.main.activity_recipe_list.*
import kotlinx.android.synthetic.main.toolbar.view.*


class RecipeListActivity : AppCompatActivity() {

    private lateinit var viewModel: RecipeListViewModel
    private lateinit var recipeListAdapter: RecipeListAdapter
    private lateinit var txtQuery: String

    private var onQueryChangeListener: OnQueryChangeListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        txtQuery = intent.getStringExtra(EXTRA_TXTQUERY) ?: ""
        viewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)

        onQueryChangeListener = viewModel
        onQueryChangeListener?.onQueryChanged(txtQuery)

    }

    override fun onResume() {
        super.onResume()
        initToolbar()
        initRecyclerView()
        observeData()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbarRecipeList as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        toolbarRecipeList.tvToolbarTitle.text = txtQuery
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initRecyclerView() {
        rvRecipe.apply {
            recipeListAdapter = RecipeListAdapter(arrayListOf())
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = recipeListAdapter

            itemClick()
        }
    }

    private fun itemClick() {
        recipeListAdapter.onItemClickListener = object : RecipeListAdapter.OnItemClickListener {
            override fun onItemClicked(url: String, recipeName: String) {
                Intent(this@RecipeListActivity, RecipeWebViewActivity::class.java).apply {
                    putExtra(EXTRA_RECIPE_URL, url)
                    putExtra(EXTRA_RECIPE_NAME, recipeName)
                    startActivity(this)
                }
            }
        }
    }

    private fun observeData() {
        viewModel.apply {
            recipes.observe(this@RecipeListActivity, Observer { dataList ->
                updateList(dataList)
            })
            error.observe(this@RecipeListActivity, Observer { isError ->
                displayError(isError)
            })
        }
    }

    private fun updateList(dataList: List<Data>) {
        if (dataList.isNotEmpty()) {
            recipeListAdapter.updateList(dataList)
        } else {
            tvNotFound.visible()
        }
        loading.gone()
    }

    private fun displayError(isError: Boolean) {
        if (isError) {
            this.makeToast(resources.getString(R.string.error_message))
            loading.gone()
            tvError.visible()
        }
    }

    companion object {
        const val EXTRA_TXTQUERY = "txtQuery"
    }
}
