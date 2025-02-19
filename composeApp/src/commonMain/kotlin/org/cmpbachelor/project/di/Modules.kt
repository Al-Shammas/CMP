package org.cmpbachelor.project.di

import org.cmpbachelor.project.catalog.data.network.KtorRemoteProductDataSource
import org.cmpbachelor.project.catalog.data.network.RemoteProductDataSource
import org.cmpbachelor.project.catalog.data.repository.DefaultProductRepository
import org.cmpbachelor.project.catalog.domain.ProductRepository
import org.cmpbachelor.project.catalog.presentation.product_list.ProductListViewModel
import org.cmpbachelor.project.home.presentation.HomeViewModel
import org.cmpbachelor.project.core.data.HttpClientFactory
import org.cmpbachelor.project.Greeting
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule =
    module {
        single { HttpClientFactory.create(get()) }
        singleOf(::KtorRemoteProductDataSource).bind<RemoteProductDataSource>()
        singleOf(::DefaultProductRepository).bind<ProductRepository>()
        singleOf(::Greeting)
        viewModelOf(::ProductListViewModel)
        viewModelOf(::HomeViewModel)
    }
