package com.fahmisbas.findmerecipe.ui.activities.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fahmisbas.findmerecipe.R
import com.fahmisbas.findmerecipe.ui.activities.recipelist.RecipeListActivity
import com.fahmisbas.findmerecipe.ui.activities.recipelist.RecipeListActivity.Companion.EXTRA_TXTQUERY
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search()
    }

    private fun search() {
        btnSearch.setOnClickListener {
            navigateToRecipeList()
        }
        edtQuery.setOnEditorActionListener { _, _, _ ->
            navigateToRecipeList()
            true
        }
    }

    private fun navigateToRecipeList() {
        Intent(this, RecipeListActivity::class.java).apply {
            if (edtQuery.editableText.toString().isNotEmpty()) {
                val txtQuery = edtQuery.editableText.toString().trim()
                putExtra(EXTRA_TXTQUERY, txtQuery)
                startActivity(this)
            } else {
                edtQuery.error = resources.getString(R.string.warning_empty_field_message)
            }
        }
    }
}