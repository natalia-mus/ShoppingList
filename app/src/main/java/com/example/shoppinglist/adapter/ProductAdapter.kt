package com.example.shoppinglist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.model.Product
import kotlinx.android.synthetic.main.product_item.view.*

class ProductAdapter(
    val context: Context,
    val products: List<Product>,
    val onItemClickAction: OnItemClickAction
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productName.text = products[position].name
        holder.productQuantity.text = products[position].quantity
        holder.productPriority.text = products[position].priority.toString()
    }

    override fun getItemCount() = products.size


    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnLongClickListener {
                onItemClickAction.onItemLongClicked(products[adapterPosition])
                return@setOnLongClickListener true
            }

            view.product_button_delete.setOnClickListener {
                onItemClickAction.onDeleteClicked(products[adapterPosition].id)
            }
        }

        val productName:TextView = view.product_name
        val productQuantity:TextView = view.product_quantity
        val productPriority:TextView = view.product_priority
    }

}

interface OnItemClickAction {
    fun onDeleteClicked(productId: Int)
    fun onItemLongClicked(product: Product)
}