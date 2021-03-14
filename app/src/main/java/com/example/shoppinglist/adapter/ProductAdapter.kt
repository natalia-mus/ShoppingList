package com.example.shoppinglist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.model.Product
import kotlinx.android.synthetic.main.product_item.view.*

class ProductAdapter(val context: Context, val products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val inflatedLayoutInflater = layoutInflater.inflate(R.layout.product_item, parent, false)

        return ProductViewHolder(inflatedLayoutInflater)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productName.text = products[position].name
        holder.productAmount.text = products[position].amount
        holder.productPriority.text = products[position].priority.toString()
    }

    override fun getItemCount() = products.size

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val productName = view.product_name
        val productAmount = view.product_amount
        val productPriority = view.product_priority
    }

}