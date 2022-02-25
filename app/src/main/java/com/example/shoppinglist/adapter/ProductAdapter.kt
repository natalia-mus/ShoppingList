package com.example.shoppinglist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.model.Product
import com.example.shoppinglist.view.DeleteItemDialog
import kotlinx.android.synthetic.main.product_item.view.*

class ProductAdapter(
    val context: Context,
    val mainView: MainActivityContract.MainActivityView,
    val products: List<Product>,
    val onItemClickAction: OnItemClickAction
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val inflatedLayoutInflater = layoutInflater.inflate(R.layout.product_item, parent, false)

        return ProductViewHolder(inflatedLayoutInflater)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productName.text = products[position].name
        holder.productQuantity.text = products[position].quantity
        holder.productPriority.text = products[position].priority.toString()
    }

    override fun getItemCount() = products.size


    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnLongClickListener() {
                onItemClickAction.onItemLongClicked(products[adapterPosition])
                return@setOnLongClickListener true
            }

            view.product_button_delete.setOnClickListener() {
                val productId = products[adapterPosition].id
                val dialog = DeleteItemDialog(context, mainView, productId)
                dialog.show()
            }
        }

        val productName = view.product_name
        val productQuantity = view.product_quantity
        val productPriority = view.product_priority
    }

}

interface OnItemClickAction {
    fun onItemLongClicked(product: Product)
}