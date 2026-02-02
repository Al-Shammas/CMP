package org.cmp.project

import android.app.Application
import org.cmp.project.di.initKoin
import org.koin.android.ext.koin.androidContext

class CmpApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CmpApp)
        }
    }
}