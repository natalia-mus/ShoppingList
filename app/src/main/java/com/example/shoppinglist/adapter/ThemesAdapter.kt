package com.example.shoppinglist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoppinglist.R
import com.example.shoppinglist.model.Theme
import kotlinx.android.synthetic.main.theme_item.view.*

class ThemesAdapter(private val context: Context, private val themes: ArrayList<Theme>) :
    RecyclerView.Adapter<ThemesAdapter.ThemesAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemesAdapterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.theme_item, parent, false)
        return ThemesAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ThemesAdapterViewHolder, position: Int) {
        holder.themeName.text = themes[position].name
        val imageSource = getImageSource(themes[position].listBackground)
        imageSource?.let { holder.themeImage.setImageResource(it) }
    }

    override fun getItemCount() = themes.size

    private fun getImageSource(image: String): Int? {
        return image.toIntOrNull()
    }


    inner class ThemesAdapterViewHolder(view: View) : ViewHolder(view) {
        val themeImage: ImageView = view.theme_image
        val themeName: TextView = view.theme_name
    }
}

