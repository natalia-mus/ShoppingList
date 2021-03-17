package com.example.shoppinglist

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import com.example.shoppinglist.contract.MainActivityContract
import kotlinx.android.synthetic.main.delete_item_dialog.*

class DeleteItemDialog(
    context: Context,
    val mainView: MainActivityContract.MainActivityView,
    val id: Int
) : AlertDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_item_dialog)

        delete_item_no.setOnClickListener() {
            dismiss()
        }

        delete_item_yes.setOnClickListener() {
            mainView.deleteItem(id)
            mainView.showData()
            onBackPressed()
        }
    }
}