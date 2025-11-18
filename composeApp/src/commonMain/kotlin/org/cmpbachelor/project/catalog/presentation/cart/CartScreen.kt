package org.cmpbachelor.project.catalog.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.cmpbachelor.project.catalog.data.database.CartItemEntity
import org.cmpbachelor.project.catalog.presentation.cart.componants.CartItemRow
import org.cmpbachelor.project.core.presentation.AquaGreenColor
import org.cmpbachelor.project.core.presentation.GoldYellow
import org.cmpbachelor.project.core.presentation.PaleBlueColor
import org.cmpbachelor.project.core.presentation.roundTo
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CartScreenRoot(
    viewModel: CartViewModel,
    onCheckout: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = PaleBlueColor
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
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(PaleBlueColor),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = AquaGreenColor)
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PaleBlueColor)
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        tint = AquaGreenColor,
                        modifier = Modifier.size(32.dp)
                    )
                    Column {
                        Text(
                            text = "Shopping Cart",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "${state.items.size} item${if (state.items.size != 1) "s" else ""}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        if (state.items.isEmpty()) {
            // Empty cart state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = Color.Gray.copy(alpha = 0.3f)
                    )
                    Text(
                        text = "Your cart is empty",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Text(
                        text = "Add some products to get started!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            // Cart items list
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(state.items) { item ->
                    CartItemRow(
                        item = item,
                        onIncrease = {
                            onAction(CartAction.IncreaseQuantity(item.productId))
                        },
                        onDecrease = {
                            onAction(CartAction.DecreaseQuantity(item.productId))
                        },
                        onRemove = {
                            onAction(CartAction.RemoveItem(item.productId))
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Bottom checkout section
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 8.dp,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp, bottom = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Order summary
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "$${state.totalPrice.roundTo(2)}",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = GoldYellow
                            )
                        }
                    }

                    // Checkout button
                    Button(
                        onClick = onCheckout,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp).padding(start = 20.dp, end = 20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AquaGreenColor
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Proceed to Checkout",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview()
@Composable
fun CartScreenWithItemsPreview() {
    CartScreen(
        state = CartState(
            isLoading = false,
            items = listOf(
                CartItemEntity(
                    productId = 1,
                    title = "iPhone 9",
                    price = 549.0,
                    thumbnail = "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg",
                    quantity = 2
                ),
                CartItemEntity(
                    productId = 2,
                    title = "iPhone X",
                    price = 899.0,
                    thumbnail = "https://cdn.dummyjson.com/product-images/2/thumbnail.jpg",
                    quantity = 1
                ),
                CartItemEntity(
                    productId = 3,
                    title = "Samsung Universe 9",
                    price = 1249.0,
                    thumbnail = "https://cdn.dummyjson.com/product-images/3/thumbnail.jpg",
                    quantity = 1
                )
            ),
            totalPrice = 1997.0
        ),
        onAction = {},
        onCheckout = {}
    )
}