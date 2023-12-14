package com.example.shoppinglist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoppinglist.R
import com.example.shoppinglist.model.Theme
import com.example.shoppinglist.view.ThemeSelector
import kotlinx.android.synthetic.main.theme_item.view.*

class ThemesAdapter(private val context: Context, private var themes: ArrayList<Theme>, private var selectedThemeId: Int, private val themeSelector: ThemeSelector) :
    RecyclerView.Adapter<ThemesAdapter.ThemesAdapterViewHolder>() {

    private val viewHolders = ArrayList<ThemesAdapterViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemesAdapterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.theme_item, parent, false)
        val viewHolder = ThemesAdapterViewHolder(view)
        viewHolders.add(viewHolder)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ThemesAdapterViewHolder, position: Int) {
        holder.themeName.text = themes[position].name
        val imageSource = getImageSource(themes[position].listBackgroundLandscape)
        imageSource?.let { holder.themeImage.setImageResource(it) }

        if (themes[position].id == selectedThemeId) {
            refreshSelection(holder.themeItem)
        }

        holder.themeItem.setOnClickListener {
            refreshSelection(holder.themeItem)
            themes[position].id?.let { id -> themeSelector.onThemeSelected(id) }
        }
    }

    override fun getItemCount() = themes.size

    fun dataSetChanged(newDataSet: ArrayList<Theme>) {
        themes = newDataSet
        notifyDataSetChanged()
    }

    private fun getImageSource(image: String): Int? {
        return image.toIntOrNull()
    }

    private fun refreshSelection(themeItem: LinearLayout) {
        for (viewHolder in viewHolders) {
            viewHolder.themeItem.isSelected = false
        }

        themeItem.isSelected = true
    }


    inner class ThemesAdapterViewHolder(val view: View) : ViewHolder(view) {
        val themeItem: LinearLayout = view.theme_item
        val themeImage: ImageView = view.theme_image
        val themeName: TextView = view.theme_name
    }
}

