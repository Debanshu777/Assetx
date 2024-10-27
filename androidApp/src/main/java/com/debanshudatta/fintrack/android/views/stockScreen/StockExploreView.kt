package com.debanshudatta.fintrack.android.views.stockScreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.debanshudatta.fintrack.android.views.stockScreen.organism.GridIndicesView
import com.debanshudatta.fintrack.android.views.stockScreen.organism.TodayStockView
import com.debanshudatta.fintrack.AppViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun StockExploreView() {
    val viewModel: AppViewModel = koinViewModel()
    val universeState = viewModel.universeDataList.collectAsStateWithLifecycle()
    val indicesState = viewModel.indicesList.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            GridIndicesView(indicesState)
        }
        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
        item {
            TodayStockView(universeState) { tab ->
                viewModel.setType(tab)
            }
        }
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
