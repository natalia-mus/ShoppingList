package com.example.shoppinglist.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoppinglist.ImageUtils
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
        val theme = themes[position]

        holder.themeName.text = theme.name
        setImage(holder.themeImage, position)

        if (theme.id == selectedThemeId) {
            refreshSelection(holder.themeItem)
        }

        holder.themeItem.setOnClickListener {
            refreshSelection(holder.themeItem)
            themeSelector.onThemeSelected(theme.id)
        }

        if (!theme.builtInTheme) {
            holder.themeDelete.visibility = View.VISIBLE

            holder.themeDelete.setOnClickListener {
                themeSelector.onDeleteThemeClicked(theme.id)
            }
        }
    }

    private fun setImage(imageView: ImageView, position: Int) {
        val theme = themes[position]
        // find first existing background in theme
        val thumbnail = if (theme.listBackgroundImagePortrait != null) {
            ImageUtils.getImageAsBitmap(theme.listBackgroundImagePortrait)
        } else if (theme.listBackgroundColorPortrait != null) {
            theme.listBackgroundColorPortrait
        } else if (theme.listBackgroundImageLandscape != null) {
            ImageUtils.getImageAsBitmap(theme.listBackgroundImageLandscape)
        } else if (theme.listBackgroundColorLandscape != null) {
            theme.listBackgroundColorLandscape
        } else if (theme.addProductBackgroundImagePortrait != null) {
            ImageUtils.getImageAsBitmap(theme.addProductBackgroundImagePortrait)
        } else if (theme.addProductBackgroundColorPortrait != null) {
            theme.addProductBackgroundColorPortrait
        } else if (theme.addProductBackgroundImageLandscape != null) {
            ImageUtils.getImageAsBitmap(theme.addProductBackgroundImageLandscape)
        } else if (theme.addProductBackgroundColorLandscape != null) {
            theme.addProductBackgroundColorLandscape
        } else {
            null
        }

        if (thumbnail != null) {
            if (thumbnail is Bitmap) imageView.setImageBitmap(thumbnail)
            else if (thumbnail is Int) imageView.setBackgroundColor(thumbnail)
        }
    }

    override fun getItemCount() = themes.size

    fun dataSetChanged(newDataSet: ArrayList<Theme>) {
        themes = newDataSet
        notifyDataSetChanged()
    }

    private fun refreshSelection(themeItem: ConstraintLayout) {
        for (viewHolder in viewHolders) {
            viewHolder.themeItem.isSelected = false
        }

        themeItem.isSelected = true
    }


    inner class ThemesAdapterViewHolder(val view: View) : ViewHolder(view) {
        val themeItem: ConstraintLayout = view.theme_item
        val themeImage: ImageView = view.theme_image
        val themeName: TextView = view.theme_name
        val themeDelete: ImageView = view.theme_delete
    }
}

