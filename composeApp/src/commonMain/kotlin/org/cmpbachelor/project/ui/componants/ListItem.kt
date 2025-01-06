package ui.componants

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import cmpbachelor.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.material.Icon
import androidx.compose.ui.unit.dp
import cmpbachelor.composeapp.generated.resources.baseline_home_24
import cmpbachelor.composeapp.generated.resources.bin_icon
import ui.componants.ShoppingCartItemDimensions.iconSize
import ui.componants.ShoppingCartItemDimensions.margin


@OptIn(ExperimentalResourceApi::class)
@Composable
fun ShoppingCartItem(description: String, amount: Int, pricePerUnit: Double) {
    Card(
        backgroundColor = Color.LightGray,
        shape = RoundedCornerShape(30),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.padding(start = Dp(18f)),
                painter = painterResource(resource = Res.drawable.baseline_home_24),
                contentDescription = "",
            )
            Spacer(Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Spacer(Modifier.padding(top = Dp(8f)))
                Text(description)
                Row {
                    Text(text = "${amount}x ")
                    Text(
                        text = "$pricePerUnit€",
                        textDecoration = TextDecoration.Underline
                    )
                }
                Spacer(Modifier.padding(bottom = Dp(8f)))
            }
            Icon(
                painter = painterResource(resource = Res.drawable.bin_icon),
                contentDescription = "",
                modifier = Modifier.padding(all = margin).size(iconSize)
            )
        }
    }
}

private object ShoppingCartItemDimensions {
    val margin = 16.dp
    val iconSize = 24.dp
}
