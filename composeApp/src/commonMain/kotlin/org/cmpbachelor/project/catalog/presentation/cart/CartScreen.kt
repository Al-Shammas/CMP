package org.cmpbachelor.project.catalog.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.cmpbachelor.project.catalog.presentation.cart.componants.CartItemRow
import org.cmpbachelor.project.core.presentation.DesertWhite
import org.cmpbachelor.project.core.presentation.PaleBlueColor
import org.cmpbachelor.project.core.presentation.roundTo

@Composable
fun CartScreenRoot(
    viewModel: CartViewModel,
    onCheckout: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value
    Scaffold(
        snackbarHost = { SnackbarHost(remember { SnackbarHostState() }) }
    ) { padding ->
        CartScreen(
            state = state,
            onAction = viewModel::onAction,
            modifier = Modifier.padding(padding),
            onCheckout = onCheckout
        )
    }
}

@Composable
fun CartScreen(
    state: CartState,
    onAction: (CartAction) -> Unit,
    onCheckout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.isLoading) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PaleBlueColor)
    ) {
        if (state.items.isEmpty()) {
            Text(
                "Your cart is empty",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            return
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        )
        {
            items(state.items) { item ->
                CartItemRow(
                    item = item,
                    onIncrease = { onAction(CartAction.IncreaseQuantity(item.productId)) },
                    onDecrease = { onAction(CartAction.DecreaseQuantity(item.productId)) },
                    onRemove = { onAction(CartAction.RemoveItem(item.productId)) }
                )
            }
        }

        Text(
            text = "Total: ${state.totalPrice.roundTo(2)}\$",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
            },
            modifier = Modifier
                .fillMaxWidth().padding(12.dp)
                .height(48.dp).wrapContentWidth(),
            colors = ButtonDefaults.buttonColors(DesertWhite),
        ) {
            Text("Proceed to Checkout", color = Color.Black)
        }
    }
}