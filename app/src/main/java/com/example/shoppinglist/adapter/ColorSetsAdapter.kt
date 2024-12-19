package com.example.shoppinglist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.ThemeConstants
import com.example.shoppinglist.model.ColorSet

class ColorSetsAdapter(private val context: Context, private val colorSets: ArrayList<ColorSet>, private val colorSetSelector: ColorSetSelector) :
    RecyclerView.Adapter<ColorSetsAdapter.ColorSetsViewHolder>() {

    private var selectedItem: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorSetsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.color_set_item, parent, false)
        return ColorSetsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorSetsViewHolder, position: Int) {
        val colorSet = colorSets[position]

        holder.primaryColor.background.setTint(colorSet.primaryColorValue)
        holder.secondaryColor.background.setTint(colorSet.secondaryColorValue)
        holder.name.text = colorSet.name

        holder.colorSetItem.setOnClickListener {
            refreshSelection(holder.colorSetItem)
            colorSetSelector.onColorSetSelected(colorSet.id)
        }

        if (colorSet.id == ThemeConstants.DEFAULT_COLOR_SET_ID) {
            refreshSelection(holder.colorSetItem)
        }
    }

    override fun getItemCount() = colorSets.size

    private fun refreshSelection(view: View) {
        selectedItem?.isSelected = false
        view.isSelected = true
        selectedItem = view
    }


    inner class ColorSetsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val colorSetItem: ConstraintLayout = view.findViewById(R.id.color_set_item)
        val primaryColor: ImageView = view.findViewById(R.id.color_set_item_color_primary)
        val secondaryColor: ImageView = view.findViewById(R.id.color_set_item_color_secondary)
        val name: TextView = view.findViewById(R.id.color_set_item_name)
    }
}

interface ColorSetSelector {
    fun onColorSetSelected(colorSetId: Int)
}