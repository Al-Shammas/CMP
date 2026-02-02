package org.cmp.project.scan.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.cmp.project.scan.presentation.componants.ErrorCard
import org.cmp.project.scan.presentation.componants.FlashlightToggle
import org.cmp.project.scan.presentation.componants.QrScannerBox
import org.cmp.project.scan.presentation.componants.SuccessCard

@Composable
fun ScanScreenRoot(
    viewModel: ScanViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ScanScreen(
        state = state,
        onAction = viewModel::onAction,
        onBackClick = onBackClick
    )
}

@Composable
fun ScanScreen(
    state: ScanState,
    onAction: (ScanAction) -> Unit,
    onBackClick: () -> Unit
) {
    var flashlightOn by rememberSaveable { mutableStateOf(false) }
    var openImagePicker by remember { mutableStateOf(false) }

    val hapticFeedback = LocalHapticFeedback.current
    LaunchedEffect(state.productId) {
        if (state.productId != null) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScanHeader(onBackClick = onBackClick)

            Spacer(modifier = Modifier.height(32.dp))

            InstructionText()

            Spacer(modifier = Modifier.height(24.dp))

            QrScannerBox(
                flashlightOn = flashlightOn,
                openImagePicker = openImagePicker,
                onScanned = { code ->
                    onAction(ScanAction.OnQrCodeScanned(code))
                },
                onImagePickerStateChange = { isOpen ->
                    openImagePicker = isOpen
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            FlashlightToggle(
                flashlightOn = flashlightOn,
                onToggle = { flashlightOn = !flashlightOn }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TipText()
        }

        if (state.lastScannedCode != null && state.productId != null) {
            SuccessCard(
                scannedCode = state.lastScannedCode,
                productId = state.productId,
                onDismiss = { onAction(ScanAction.OnNavigationHandled) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }

        state.error?.let { error ->
            ErrorCard(
                error = error.asString(),
                lastScannedCode = state.lastScannedCode,
                onDismiss = { onAction(ScanAction.OnErrorDismissed) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun ScanHeader(
    onBackClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .statusBarsPadding(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Text(
            text = "Scan Product Code",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.size(48.dp))
    }
}

@Composable
private fun InstructionText() {
    Text(
        text = "Position QR code within the frame",
        style = MaterialTheme.typography.bodyLarge,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 32.dp)
    )
}

@Composable
private fun TipText() {
    Text(
        text = "💡 Tip: Make sure the code is well-lit and in focus",
        style = MaterialTheme.typography.bodySmall,
        color = Color.White.copy(alpha = 0.7f),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 32.dp)
    )
}