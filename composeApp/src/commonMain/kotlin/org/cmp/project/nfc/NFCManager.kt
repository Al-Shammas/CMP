package org.cmp.project.nfc

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.SharedFlow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class NFCManager {

    val tags: SharedFlow<String>

    @Composable
    fun Register()
}

@Composable
expect fun getNFCManager(): NFCManager
