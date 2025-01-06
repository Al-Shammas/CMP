package org.cmpbachelor.project

import androidx.compose.ui.window.ComposeUIViewController
import di.KoinInitializer

fun MainViewController() =
    ComposeUIViewController(
        configure = {
            KoinInitializer().init()
        }
    ) {
        App()
    }
