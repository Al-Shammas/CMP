package org.cmp.project.scan.presentation

import org.cmp.project.core.presentation.UiText

data class ScanState(
    val productId: Int? = null,
    val error: UiText? = null,
    val lastScannedCode: String? = null,
    val isNavigating: Boolean = false
)