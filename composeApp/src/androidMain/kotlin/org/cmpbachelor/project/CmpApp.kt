package org.cmpbachelor.project

import android.app.Application
import di.KoinInitializer

class CmpApp : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}