package org.cmpbachelor.project

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.cmpbachelor.project.navigation.SetupNavGraph
import org.cmpbachelor.project.core.presentation.componants.BottomBar
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.KoinContext

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    androidx.compose.material3.MaterialTheme {
        KoinContext {
            val navController = rememberNavController()

            Scaffold(bottomBar = {
                BottomBar(navController)
            }) {
                SetupNavGraph(navController)
            }
        }
    }
}