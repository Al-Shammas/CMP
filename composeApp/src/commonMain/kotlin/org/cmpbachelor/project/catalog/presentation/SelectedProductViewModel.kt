package org.cmpbachelor.project.catalog.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.cmpbachelor.project.catalog.domain.Product

class SelectedProductViewModel : ViewModel() {
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    fun onSelectedProduct(product: Product?) {
        _selectedProduct.value = product
    }
}