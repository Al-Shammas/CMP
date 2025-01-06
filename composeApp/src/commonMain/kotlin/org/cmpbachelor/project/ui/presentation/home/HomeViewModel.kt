package ui.presentation.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.cmpbachelor.project.Greeting

class HomeViewModel(
    diTitle: Greeting,
) : ViewModel() {
    private val _timer = MutableStateFlow(0)
    val timer = _timer.asStateFlow()

    private val _title = MutableStateFlow(diTitle.greet())
    val title = _title.asStateFlow()
}