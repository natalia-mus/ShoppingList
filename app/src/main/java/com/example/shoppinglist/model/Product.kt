package com.example.shoppinglist.model

import java.io.Serializable

data class Product(
    val id: Int,
    val name: String,
    val quantity: String,
    val priority: Int
) : Serializable