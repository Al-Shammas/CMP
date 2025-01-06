package di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ui.presentation.home.HomeViewModel

actual val viewModelModule = module {
    viewModelOf(::HomeViewModel)
}