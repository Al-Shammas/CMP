package org.cmpbachelor.project.nfc

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class NFCManager : NfcAdapter.ReaderCallback {

    private var mNfcAdapter: NfcAdapter? = null
    private val _tagData = MutableSharedFlow<String>()
    private val scope = CoroutineScope(SupervisorJob())
    actual val tags: SharedFlow<String> = _tagData

    @Composable
    actual fun Register() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(LocalContext.current)

        if (mNfcAdapter != null) {
            val options = Bundle()

            options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 500)

            mNfcAdapter!!.enableReaderMode(
                LocalContext.current as Activity,
                this,
                NfcAdapter.FLAG_READER_NFC_A or
                        NfcAdapter.FLAG_READER_NFC_B or
                        NfcAdapter.FLAG_READER_NFC_F or
                        NfcAdapter.FLAG_READER_NFC_V or
                        NfcAdapter.FLAG_READER_NFC_BARCODE or
                        NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS,
                options
            )
        }
    }

    override fun onTagDiscovered(tag: Tag?) {
        if (tag != null) {

            val techList = tag.techList
            techList.forEach {
                Log.d("NFC", "Tech: $it")
            }

            val mNdef = Ndef.get(tag)
            if (mNdef != null) {
                val mNdefMessage = mNdef.cachedNdefMessage
                if (mNdefMessage != null) {
                    val records = mNdefMessage.records
                    val ndefRecordsCount = records.size
                    if (ndefRecordsCount > 0) {
                        for (i in 0 until ndefRecordsCount) {
                            val payload = String(records[i].payload, Charsets.UTF_8)
                            scope.launch {
                                _tagData.emit(payload)
                            }
                        }
                    } else {
                        // NDEF message is empty
                        scope.launch {
                            _tagData.emit("Empty NDEF message")
                        }
                    }
                } else {
                    // NdefMessage is null
                    scope.launch {
                        _tagData.emit("No NDEF message found")
                    }
                }
            } else {
                // Non-NDEF tags, read raw ID
                val tagId = tag.id
                val tagIdString = tagId.joinToString("") { String.format("%02X", it) }
                scope.launch {
                    _tagData.emit("Non-NDEF tag detected with ID: $tagIdString")
                }
            }
        } else {
            // Null tag
            scope.launch {
                _tagData.emit("No tag detected")
            }
        }
    }

}

@Composable
actual fun getNFCManager(): NFCManager {
    return NFCManager()
}
