package org.cmpbachelor.project

import android.app.Application
import org.cmpbachelor.project.di.initKoin
import org.koin.android.ext.koin.androidContext

class CmpApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CmpApp)
        }
    }
}