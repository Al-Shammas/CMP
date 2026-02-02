package org.cmp.project

import androidx.compose.ui.window.ComposeUIViewController
import org.cmp.project.di.initKoin

fun MainViewController() =
    ComposeUIViewController(
        configure = {
            initKoin()
        }
    ) {
        App()
    }