package com.example.shoppinglist.core

import android.app.Application
import kotlin.properties.Delegates

class App : Application() {

    companion object {
        var context: App by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}