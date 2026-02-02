package org.cmp.project.scan.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ScanViewModel : ViewModel() {

    private val _state = MutableStateFlow(ScanState())
    val state = _state.asStateFlow()

    fun onAction(action: ScanAction) {
        when (action) {
            is ScanAction.OnQrCodeScanned -> {
                val productId = extractProductId(action.scannedText)
                if (productId != null) {
                    _state.update {
                        it.copy(
                            productId = productId,
                            error = null
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            error = "Invalid QR code: ${action.scannedText}",
                            productId = null
                        )
                    }
                }
            }
            is ScanAction.OnNavigationHandled -> {
                _state.update {
                    it.copy(productId = null)
                }
            }
            is ScanAction.OnErrorDismissed -> {
                _state.update {
                    it.copy(error = null)
                }
            }
        }
    }

    /**
     * Extract product ID from scanned QR code
     * Examples of valid formats:
     * - "https://yourapp.com/product/123" -> 123
     * - "https://dummyjson.com/products/5" -> 5
     * - "product:123" -> 123
     * - "123" -> 123
     */
    private fun extractProductId(scannedText: String): Int? {
        return try {
            // Try direct number first
            scannedText.toIntOrNull()
            // Try extracting from URL pattern (last number in path)
                ?: scannedText.split("/").lastOrNull()?.toIntOrNull()
                // Try extracting from "product:ID" pattern
                ?: if (scannedText.contains(":")) {
                    scannedText.split(":").lastOrNull()?.toIntOrNull()
                } else null
        } catch (e: Exception) {
            null
        }
    }
}