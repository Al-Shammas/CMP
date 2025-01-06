package org.cmpbachelor.project

import androidx.compose.runtime.*
import navigation.SetupNavGraph
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.KoinContext

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    androidx.compose.material3.MaterialTheme {
        KoinContext {
            SetupNavGraph()
        }
    }
}