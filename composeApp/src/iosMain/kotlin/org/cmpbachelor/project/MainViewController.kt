package org.cmpbachelor.project

import androidx.compose.ui.window.ComposeUIViewController
import org.cmpbachelor.project.di.initKoin

fun MainViewController() =
    ComposeUIViewController(
        configure = {
            initKoin()
        }
    ) {
        App()
    }