package com.example.shoppinglist.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.model.Icon
import com.example.shoppinglist.model.Product
import com.example.shoppinglist.model.Theme
import kotlinx.android.synthetic.main.product_item.view.*

class ProductAdapter(
    val context: Context,
    val products: List<Product>,
    val onItemClickAction: OnItemClickAction,
    val theme: Theme?
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view, theme)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productName.text = products[position].name
        holder.productQuantity.text = products[position].quantity
        holder.productPriority.text = products[position].priority.toString()
    }

    override fun getItemCount() = products.size


    inner class ProductViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        constructor(view: View, theme: Theme?) : this(view) {
            this.theme = theme

            prepareItem()

            view.setOnLongClickListener {
                onItemClickAction.onItemLongClicked(products[adapterPosition])
                return@setOnLongClickListener true
            }

            view.product_button_delete.setOnClickListener {
                onItemClickAction.onDeleteClicked(products[adapterPosition].id)
            }
        }

        private var theme: Theme? = null

        val productName: TextView = view.product_name
        val productQuantity: TextView = view.product_quantity
        val productPriority: TextView = view.product_priority
        private val productItemBackground: LinearLayout = view.product_item
        private val productQuantityLabel: TextView = view.product_quantity_label
        private val productPriorityLabel: TextView = view.product_priority_label
        private val productButtonDelete: ImageButton = view.product_button_delete


        private fun prepareItem() {
            if (theme != null) {
                productItemBackground.background.setTint(Color.parseColor(theme!!.productItemBackgroundValue))

                productName.setTextColor(theme!!.productItemTextColorValue)
                productQuantity.setTextColor(theme!!.productItemTextColorValue)
                productQuantityLabel.setTextColor(theme!!.productItemTextColorValue)
                productPriority.setTextColor(theme!!.productItemTextColorValue)
                productPriorityLabel.setTextColor(theme!!.productItemTextColorValue)


                if (theme!!.deleteIcon == Icon.TRASH_BIN) {
                    productButtonDelete.background = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_trash_bin, null)
                } else {
                    productButtonDelete.background = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_cross, null)
                }

                productButtonDelete.background.setTint(theme!!.deleteIconColorValue)


                if (theme!!.boldProductName) {
                    productName.typeface = Typeface.DEFAULT_BOLD
                } else {
                    productName.typeface = Typeface.DEFAULT
                }
            }
        }
    }

}

interface OnItemClickAction {
    fun onDeleteClicked(productId: Int)
    fun onItemLongClicked(product: Product)
}