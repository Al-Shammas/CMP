package org.cmp.project.scan.presentation

sealed interface ScanAction {
    data class OnQrCodeScanned(val scannedText: String) : ScanAction
    data object OnNavigationHandled : ScanAction
    data object OnErrorDismissed : ScanAction
}