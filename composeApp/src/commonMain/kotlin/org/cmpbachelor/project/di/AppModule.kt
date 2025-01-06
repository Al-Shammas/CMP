package di

import org.cmpbachelor.project.Greeting
import org.koin.dsl.module

val appModule =
    module {
        single { Greeting() }
    }
