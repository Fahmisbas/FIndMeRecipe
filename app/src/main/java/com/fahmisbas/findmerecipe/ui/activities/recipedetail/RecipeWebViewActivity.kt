package com.fahmisbas.findmerecipe.ui.activities.recipedetail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.fahmisbas.findmerecipe.R
import com.fahmisbas.findmerecipe.utils.gone
import kotlinx.android.synthetic.main.activity_recipe_web_view.*


class RecipeWebViewActivity : AppCompatActivity() {

    private var title: String? = null
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_web_view)

        initToolbar()
        displayWebView()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbarRecipeDetail as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = intent.getStringExtra(EXTRA_RECIPE_NAME)
        supportActionBar?.title = title
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun displayWebView() {
        url = intent.getStringExtra(EXTRA_RECIPE_URL)
        webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webviewLoading.gone()
            }
        }
        webview.loadUrl(url)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share) {
            shareRecipe()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareRecipe() {
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, url)
            startActivity(Intent.createChooser(this, "Share to"))
        }
    }

    companion object {
        const val EXTRA_RECIPE_URL = "recipeUrl"
        const val EXTRA_RECIPE_NAME = "recipeName"
    }
}
