package com.example.shoppinglist.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.example.shoppinglist.R
import kotlinx.android.synthetic.main.confirmation_dialog.*

class ConfirmationDialog(
    context: Context,
    private val question: String,
    private val confirmationDialogListener: ConfirmationDialogListener,
) : AlertDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirmation_dialog)
        findViewById<TextView>(R.id.confirmation_dialog_question).text = question

        confirmation_dialog_question_confirm.setOnClickListener {
            confirmationDialogListener.onConfirmButtonClick()
            dismiss()
        }

        confirmation_dialog_question_decline.setOnClickListener {
            confirmationDialogListener.onDeclineButtonClick()
            dismiss()
        }
    }
}

interface ConfirmationDialogListener {
    fun onConfirmButtonClick()
    fun onDeclineButtonClick()
}