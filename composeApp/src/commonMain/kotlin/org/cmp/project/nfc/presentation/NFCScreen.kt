package org.cmp.project.nfc.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.cmp.project.nfc.getNFCManager

@Composable
fun NFCScreen() {
    val scope = rememberCoroutineScope()
    val nfcManager = getNFCManager()

    val trigger = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }
    val tag = remember { mutableStateOf("") }

    scope.launch {
        nfcManager.tags.collectLatest { tagData ->
            println("Test: I have detected a tag  $tagData")
            tag.value = tagData
            showDialog.value = true
        }
    }

    if (trigger.value) {
        nfcManager.Register()
        trigger.value = false
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Button(onClick = {
                trigger.value = true
            }) {
                Text("Read NFC Tag")
            }
            Spacer(Modifier.padding(top = 12.dp))
            Text("Click and bring the card to the back of the phone")
        }
    }

    if (showDialog.value) {
        AlertDialog(onDismissRequest = {
            showDialog.value = false
        }, title = {
            Text("NFC Tag")
        }, text = {

            Column {
                Text("NFC tag value is: ")
                Text(tag.value ?: "")
            }
        }, confirmButton = {
            Text(text = "OK", modifier = Modifier.padding(vertical = 8.dp).clickable {
                showDialog.value = false
            })
        })
    }
}
