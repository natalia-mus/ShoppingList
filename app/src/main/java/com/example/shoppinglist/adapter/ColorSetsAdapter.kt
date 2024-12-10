package com.example.shoppinglist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.model.ColorSet

class ColorSetsAdapter(private val context: Context, private val colorSets: ArrayList<ColorSet>) : RecyclerView.Adapter<ColorSetsAdapter.ColorSetsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorSetsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.color_set_item, parent, false)
        return ColorSetsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorSetsViewHolder, position: Int) {
        val colorSet = colorSets[position]

        holder.primaryColor.background.setTint(colorSet.primaryColorValue)
        holder.secondaryColor.background.setTint(colorSet.secondaryColorValue)
        holder.name.text = colorSet.name
    }

    override fun getItemCount() = colorSets.size


    inner class ColorSetsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val primaryColor: ImageView = view.findViewById(R.id.color_set_item_color_primary)
        val secondaryColor: ImageView = view.findViewById(R.id.color_set_item_color_secondary)
        val name: TextView = view.findViewById(R.id.color_set_item_name)
    }
}