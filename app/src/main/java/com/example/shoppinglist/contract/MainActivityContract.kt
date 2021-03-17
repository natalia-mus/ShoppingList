package com.example.shoppinglist.contract

import com.example.shoppinglist.database.DBHelper
import com.example.shoppinglist.model.Product

interface MainActivityContract {

    interface MainActivityModel {
        fun fetchDataFromDB()                       // pobierze dane z bazy danych
        fun returnData(): List<Product>             // przekaże dane do presentera
        fun deleteItemFromDB(id: Int)               // usunie produkt z bazy danych
    }

    interface MainActivityPresenter {
        fun fetchData()                             // odbierze dane z modelu
        fun returnData(): List<Product>             // przekaże dane widokowi
        fun showData()                              // wywoła funkcję odpowiadającą za wyświetlanie danych
        fun deleteItem(id: Int)
    }

    interface MainActivityView {
        fun initView()                              // zainicjuje widok
        fun DBinstance(): DBHelper?                 // zwróci instancję do bazy danych
        fun showData()                              // zaktualizuje wyświetlane elementy
        fun deleteItem(id: Int)
    }

}