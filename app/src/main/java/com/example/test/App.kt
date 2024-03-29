package com.example.test

import android.app.Application
import com.example.test.di.myModule
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(myModule))
    }
}