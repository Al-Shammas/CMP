package org.cmpbachelor.project.catalog.presentation.cart.componants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import org.cmpbachelor.project.catalog.data.database.CartItemEntity
import org.cmpbachelor.project.core.presentation.DesertWhite
import org.cmpbachelor.project.core.presentation.roundTo

@Composable
fun CartItemRow(
    item: CartItemEntity,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = rememberAsyncImagePainter(item.thumbnail),
            contentDescription = item.title,
            modifier = Modifier
                .size(64.dp)
                .background(DesertWhite, RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f).padding(start = 8.dp)
        )

        IconButton(onClick = onDecrease) {
            Text("-", style = MaterialTheme.typography.bodyLarge)
        }
        Text("${item.quantity}", style = MaterialTheme.typography.bodyLarge)
        IconButton(onClick = onIncrease) {
            Text("+", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(Modifier.width(16.dp))

        Text(
            text = "${(item.quantity * item.price).roundTo(2)}\$",
            style = MaterialTheme.typography.bodyMedium
        )

        IconButton(onClick = onRemove) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}