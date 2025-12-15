package org.cmpbachelor.project.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.cmpbachelor.project.catalog.data.database.DatabaseFactory
import org.cmpbachelor.project.catalog.data.database.ShoppingCartDatabase
import org.cmpbachelor.project.catalog.data.network.KtorRemoteProductDataSource
import org.cmpbachelor.project.catalog.data.network.RemoteProductDataSource
import org.cmpbachelor.project.catalog.data.repository.DefaultProductRepository
import org.cmpbachelor.project.catalog.data.repository.DefaultShoppingCartRepository
import org.cmpbachelor.project.catalog.domain.ProductRepository
import org.cmpbachelor.project.catalog.domain.ShoppingCartRepository
import org.cmpbachelor.project.catalog.presentation.SelectedProductViewModel
import org.cmpbachelor.project.catalog.presentation.cart.CartViewModel
import org.cmpbachelor.project.catalog.presentation.product_details.ProductDetailViewModel
import org.cmpbachelor.project.catalog.presentation.product_list.ProductListViewModel
import org.cmpbachelor.project.core.data.HttpClientFactory
import org.cmpbachelor.project.scan.presentation.ScanViewModel
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
        singleOf(::DefaultShoppingCartRepository).bind<ShoppingCartRepository>()

        single {
            get<DatabaseFactory>().create()
                .setDriver(BundledSQLiteDriver())
                .fallbackToDestructiveMigration(true)
                .build()
        }
        single { get<ShoppingCartDatabase>().favoriteProductDao }
        single { get<ShoppingCartDatabase>().shoppingCartDao }

        viewModelOf(::ProductListViewModel)
        viewModelOf(::SelectedProductViewModel)
        viewModelOf(::ProductDetailViewModel)
        viewModelOf(::CartViewModel)
        viewModelOf(::ScanViewModel)
    }