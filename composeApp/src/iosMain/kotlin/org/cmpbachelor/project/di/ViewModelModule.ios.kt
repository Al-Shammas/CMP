package di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ui.presentation.home.HomeViewModel


actual val viewModelModule = module {
    singleOf(::HomeViewModel)
}