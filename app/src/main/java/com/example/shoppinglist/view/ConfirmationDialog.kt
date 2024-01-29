package com.example.shoppinglist.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.example.shoppinglist.R
import kotlinx.android.synthetic.main.delete_item_dialog.*

class ConfirmationDialog(
    context: Context,
    private val question: String,
    private val deleteItemDialogListener: DeleteItemDialogListener,
) : AlertDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_item_dialog)
        findViewById<TextView>(R.id.confirmation_dialog_question).text = question

        confirmation_dialog_question_confirm.setOnClickListener {
            deleteItemDialogListener.onConfirmButtonClick()
            dismiss()
        }

        confirmation_dialog_question_decline.setOnClickListener {
            deleteItemDialogListener.onDeclineButtonClick()
            dismiss()
        }
    }
}

interface DeleteItemDialogListener {
    fun onConfirmButtonClick()
    fun onDeclineButtonClick()
}