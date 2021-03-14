package com.example.shoppinglist.contract

import com.example.shoppinglist.database.DBHelper
import com.example.shoppinglist.model.Product

interface MainActivityContract {

    interface MainActivityModel {
        fun fetchDataFromDB()                        // pobierze dane z DB
        fun returnData(): List<Product>             // przekaże dane do presentera
    }

    interface MainActivityPresenter {
        fun fetchData()                             // odbierze dane z modelu
        fun returnData(): List<Product>             // przekaże dane widokowi
        fun showData()                              // wywoła funkcję odpowiadającą za wyświetlanie danych
    }

    interface MainActivityView {
        //fun initView()                            // zainicjuje widok
        fun showData()                              // zaktualizuje wyświetlane elementy
        fun createDB(): DBHelper                    // utworzy bazę danych
    }
}