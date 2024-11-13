package com.example.shoppinglist.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
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

        if (theme.builtInTheme) {
            holder.themeDelete.visibility = View.GONE

        } else {
            holder.themeDelete.visibility = View.VISIBLE

            holder.themeDelete.setOnClickListener {
                themeSelector.onDeleteThemeClicked(theme.id)
            }
        }
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun setImage(imageView: ImageView, position: Int) {
        val defaultThumbnail = ResourcesCompat.getDrawable(context.resources, R.drawable.theme_grocery_list_portrait, null)
        imageView.setImageDrawable(null)
        imageView.background = defaultThumbnail

        val theme = themes[position]
        // find first existing background in theme
        val thumbnail = if (theme.listBackgroundImagePortrait != null) {
            ImageUtils.getImageAsBitmap(theme.listBackgroundImagePortrait)
        } else theme.listBackgroundColorPortrait
            ?: if (theme.listBackgroundImageLandscape != null) {
                ImageUtils.getImageAsBitmap(theme.listBackgroundImageLandscape)
            } else theme.listBackgroundColorLandscape
                ?: if (theme.addProductBackgroundImagePortrait != null) {
                    ImageUtils.getImageAsBitmap(theme.addProductBackgroundImagePortrait)
                } else theme.addProductBackgroundColorPortrait
                    ?: if (theme.addProductBackgroundImageLandscape != null) {
                        ImageUtils.getImageAsBitmap(theme.addProductBackgroundImageLandscape)
                    } else theme.addProductBackgroundColorLandscape ?: defaultThumbnail

        when (thumbnail) {
            is Bitmap -> imageView.setImageBitmap(thumbnail)
            is Int -> imageView.background.setTint(thumbnail)
            is Drawable -> imageView.setImageDrawable(thumbnail)
        }
    }

    override fun getItemCount() = themes.size

    fun dataSetChanged(newDataSet: ArrayList<Theme>) {
        themes = newDataSet
        notifyDataSetChanged()
    }

    fun setSelectedThemeId(themeId: Int) {
        selectedThemeId = themeId

        for (viewHolder in viewHolders) {
            if (viewHolder.adapterPosition == selectedThemeId) {
                refreshSelection(viewHolder.themeItem)
                break
            }
        }
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

