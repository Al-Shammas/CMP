package org.cmp.project.scan.presentation.componants

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.cmp.project.core.presentation.AquaGreenColor
import qrscanner.QrScanner

@Composable
fun QrScannerBox(
    flashlightOn: Boolean,
    openImagePicker: Boolean,
    onScanned: (String) -> Unit,
    onImagePickerStateChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(280.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(
                width = 3.dp,
                color = AquaGreenColor,
                shape = RoundedCornerShape(24.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        QrScanner(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp)),
            flashlightOn = flashlightOn,
            openImagePicker = openImagePicker,
            onCompletion = onScanned,
            imagePickerHandler = onImagePickerStateChange,
            onFailure = { error ->
                println("QR Scanner error: ${error.ifEmpty { "Unknown scanner error" }}")
            }
        )
    }
}