package org.cmp.project.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.cmp.project.Greeting

class HomeViewModel(
   private val greeting: Greeting
) : ViewModel() {
    private val _timer = MutableStateFlow("Injecting Live Data")
    val timer = _timer.asStateFlow()

    private val _title = MutableStateFlow(greeting.greet())
    val title = _title.asStateFlow()

    init {
        testTimer()
    }

    private fun testTimer() {
        viewModelScope.launch {
            delay(2000)
            _timer.value = "Live Data successfully Injected"
        }
    }
}