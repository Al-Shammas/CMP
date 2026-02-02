package org.cmp.project.scan.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.cmp.project.core.presentation.UiText

class ScanViewModel : ViewModel() {

    private val _state = MutableStateFlow(ScanState())
    val state = _state.asStateFlow()

    fun onAction(action: ScanAction) {
        when (action) {
            is ScanAction.OnQrCodeScanned -> handleQrCodeScanned(action.scannedText)
            is ScanAction.OnNavigationHandled -> clearNavigation()
            is ScanAction.OnErrorDismissed -> clearError()
        }
    }

    private fun handleQrCodeScanned(scannedText: String) {
        val productId = extractProductId(scannedText)

        _state.update {
            it.copy(
                productId = productId,
                error = if (productId == null) {
                    UiText.DynamicString("Invalid QR code format")
                } else null,
                lastScannedCode = scannedText,
                isNavigating = productId != null
            )
        }
    }

    private fun clearNavigation() {
        _state.update {
            it.copy(
                productId = null,
                isNavigating = false
            )
        }
    }

    private fun clearError() {
        _state.update {
            it.copy(error = null)
        }
    }

    /**
     Extract product ID from scanned QR code.
     Supported formats:
      Direct number: "123"
      URL path: "https://yourapp.com/product/123"
      URL query param: "https://yourapp.com/product?id=123"
      Colon format: "product:123"
      Alphanumeric: "PROD-123", "ITEM123"
     */
    private fun extractProductId(scannedText: String): Int? {
        return try {
            scannedText.trim().toIntOrNull()
            // Try URL path extraction (last segment)
                ?: extractFromUrlPath(scannedText)
                // Try query parameter (?id=123)
                ?: extractFromQueryParam(scannedText)
                // Try colon format (product:123)
                ?: extractFromColonFormat(scannedText)
                // Try alphanumeric extraction (PROD-123)
                ?: extractFromAlphanumeric(scannedText)
        } catch (e: Exception) {
            null
        }
    }

    private fun extractFromUrlPath(text: String): Int? {
        return text.split("/").lastOrNull()?.toIntOrNull()
    }

    private fun extractFromQueryParam(text: String): Int? {
        return text.substringAfter("id=", "")
            .substringBefore("&")
            .trim()
            .toIntOrNull()
    }

    private fun extractFromColonFormat(text: String): Int? {
        return if (text.contains(":")) {
            text.split(":").lastOrNull()?.trim()?.toIntOrNull()
        } else null
    }

    private fun extractFromAlphanumeric(text: String): Int? {
        // Extract first number from formats like "PROD-123", "ITEM123"
        return Regex("\\d+").find(text)?.value?.toIntOrNull()
    }
}