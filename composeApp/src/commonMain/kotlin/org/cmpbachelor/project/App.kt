package org.cmpbachelor.project

import androidx.compose.runtime.Composable
import org.cmpbachelor.project.navigation.SetupNavGraph
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    androidx.compose.material3.MaterialTheme {
        SetupNavGraph()
    }
}