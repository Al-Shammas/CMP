package org.cmpbachelor.project.catalog.presentation.product_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmpbachelor.composeapp.generated.resources.Res
import cmpbachelor.composeapp.generated.resources.description
import cmpbachelor.composeapp.generated.resources.description_unavailable
import cmpbachelor.composeapp.generated.resources.prise
import cmpbachelor.composeapp.generated.resources.rating
import org.cmpbachelor.project.catalog.presentation.product_details.componants.BlurredImageBackground
import org.cmpbachelor.project.catalog.presentation.product_details.componants.ProductChip
import org.cmpbachelor.project.catalog.presentation.product_details.componants.TitledContent
import org.cmpbachelor.project.core.presentation.DesertWhite
import org.cmpbachelor.project.core.presentation.GoldYellow
import org.jetbrains.compose.resources.stringResource
import kotlin.math.round

@Composable
fun ProductDetailScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: ProductDetailViewModel,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.uiEvents) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar ->
                    snackBarHostState.showSnackbar(event.message)
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        ProductDetailScreen(
            state = state,
            onAction = { action ->
                when (action) {
                    is ProductDetailAction.OnBackClick -> onBackClick()
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
    }
}

@Composable
private fun ProductDetailScreen(
    state: ProductDetailsState,
    onAction: (ProductDetailAction) -> Unit,
) {
    BlurredImageBackground(
        imageUrl = state.product?.thumbnail,
        isFavorite = state.isFavorite,
        onFavoriteClick = {
            onAction(ProductDetailAction.OnFavoriteClick)
        },
        onBackClick = {
            onAction(ProductDetailAction.OnBackClick)
        },
        modifier = Modifier.fillMaxSize(),
        content = {
            if (state.product != null) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 700.dp)
                        .fillMaxWidth()
                        .padding(
                            vertical = 16.dp,
                            horizontal = 16.dp
                        )
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.product.title,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(44.dp))

                    Row(
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        state.product.rating?.let { rating ->
                            TitledContent(
                                title = stringResource(Res.string.rating),
                            ) {
                                ProductChip {
                                    Text(
                                        text = "${round(rating * 10) / 10.0}"
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = GoldYellow
                                    )
                                }
                            }
                        }
                        TitledContent(
                            title = stringResource(Res.string.prise),
                        ) {
                            ProductChip {
                                Text(text = "${state.product.price}$")
                            }
                        }

                    }
                    Spacer(modifier = Modifier.height(44.dp))
                    Text(
                        text = stringResource(Res.string.description),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .fillMaxWidth()
                            .padding(
                                top = 24.dp,
                                bottom = 8.dp
                            )
                    )
                    if (state.isLoading) {
                        CircularProgressIndicator()
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(1f),
//                        contentAlignment = Alignment.Center
//                    ) {
//                    }
                    } else {
                        Text(
                            text = if (state.product.description.isNullOrBlank()) {
                                stringResource(Res.string.description_unavailable)
                            } else {
                                state.product.description
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Justify,
                            color = if (state.product.description.isNullOrBlank()) {
                                Color.Black.copy(alpha = 0.4f)
                            } else Color.Black,
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(44.dp))
                    Button(
                        modifier = Modifier
                            .height(48.dp)
                            .wrapContentWidth(),
                        colors = ButtonDefaults.buttonColors(DesertWhite),
                        onClick = {
                            onAction(ProductDetailAction.AddToShoppingCart)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Shopping Cart",
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Add to Shopping Cart", color = Color.Black)
                    }
                }
            }
        },
    )
}