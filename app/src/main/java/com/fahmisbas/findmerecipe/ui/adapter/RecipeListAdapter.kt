package com.fahmisbas.findmerecipe.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fahmisbas.findmerecipe.R
import com.fahmisbas.findmerecipe.data.Data
import kotlinx.android.synthetic.main.item_recipe.view.*


class RecipeListAdapter(private val data: ArrayList<Data>) :
    RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null
    private val baseImageUrl = "https://spoonacular.com/recipeImages/"

    fun updateList(data: List<Data>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = data[position]

        holder.itemView.apply {
            tvRecipeName.text = data.name
            tvServings.text = "Servings ${data.servings}"
            tvReady.text = "Ready in ${data.readyInMinutes} minutes"

            Glide.with(context)
                .load("$baseImageUrl${data.imageName}")
                .apply(RequestOptions().override(150, 150))
                .into(imgRecipe)

            recipeDetail.setOnClickListener {
                onItemClickListener?.onItemClicked(
                    data.sourceUrl,
                    data.name
                )
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener {
        fun onItemClicked(url: String, recipeName: String)
    }
}