package org.cmp.project.catalog.presentation.product_list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp.composeapp.generated.resources.Res
import cmp.composeapp.generated.resources.broken_image_24
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import org.cmp.project.catalog.domain.Product
import org.cmp.project.core.presentation.AquaGreenColor
import org.cmp.project.core.presentation.DesertWhite
import org.cmp.project.core.presentation.GoldYellow
import org.cmp.project.core.presentation.LightCyanColor
import org.cmp.project.core.presentation.PaleBlueColor
import org.jetbrains.compose.resources.painterResource
import kotlin.math.round

@Composable
fun ProductListItem(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(DesertWhite),
        border = BorderStroke(
            1.dp,
            Brush.horizontalGradient(
                listOf(
                    AquaGreenColor,
                    LightCyanColor,
                    PaleBlueColor
                )
            )
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var imageLoadResult by remember {
                mutableStateOf<Result<Painter>?>(null)
            }
            val painter = rememberAsyncImagePainter(
                model = product.thumbnail,
                onSuccess = {
                    imageLoadResult =
                        if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                            Result.success(it.painter)
                        } else {
                            Result.failure(Exception("Invalid image size"))
                        }
                },
                onError = {
                    it.result.throwable.printStackTrace()
                    imageLoadResult = Result.failure(it.result.throwable)
                }
            )

            val painterState by painter.state.collectAsStateWithLifecycle()
            val transition by animateFloatAsState(
                targetValue = if (painterState is AsyncImagePainter.State.Success) 1f else 0f,
                animationSpec = tween(durationMillis = 800)
            )

            when (val result = imageLoadResult) {
                null -> CircularProgressIndicator()
                else -> {
                    Image(
                        painter = if (result.isSuccess) painter
                        else painterResource(Res.drawable.broken_image_24),
                        contentDescription = product.title,
                        contentScale = if (result.isSuccess) ContentScale.Crop else ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .graphicsLayer {
                                rotationX = (1f - transition) * 30f
                                val scale = 0.8f + (0.2f * transition)
                                scaleX = scale
                                scaleY = scale
                            }
                    )
                }
            }

            Text(
                text = product.title,
                style = MaterialTheme.typography.titleMedium.copy(color = Color.Black),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
            ) {
                Text(
                    text = "${product.price}€",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                product.rating?.let { rating ->
                    Text("  ", style = MaterialTheme.typography.bodyMedium)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${round(rating * 10) / 10.0}",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = GoldYellow
                        )
                    }
                }
            }
        }
    }
}
