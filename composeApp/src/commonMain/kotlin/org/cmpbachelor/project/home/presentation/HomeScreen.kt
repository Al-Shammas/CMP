package org.cmpbachelor.project.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.cmpbachelor.project.core.presentation.componants.NavigationListItem
import org.cmpbachelor.project.home.domain.HomeScreenItemsList
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController,
) {

    val timer by viewModel.timer.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = viewModel.title.value,
                modifier = Modifier
                    .fillMaxWidth().size(60.dp)
                    .padding(horizontal = 16.dp),
                fontWeight = FontWeight.Bold
            )
            Text(text = timer)
        }

        items(items = HomeScreenItemsList) { it ->
            NavigationListItem(title = it.tile, modifier = Modifier.clickable {
                navController.navigate(it.route)
            })
        }
    }
}