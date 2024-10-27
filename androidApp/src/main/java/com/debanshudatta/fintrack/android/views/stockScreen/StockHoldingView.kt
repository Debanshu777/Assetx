package com.debanshudatta.fintrack.android.views.stockScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.debanshudatta.fintrack.AppViewModel
import com.debanshudatta.fintrack.DataState
import org.koin.androidx.compose.koinViewModel

@Composable
fun StockHoldingView() {
    val viewModel: AppViewModel = koinViewModel()
    val assetState = viewModel.stockAssetData.collectAsStateWithLifecycle()
    when (assetState.value) {
        is DataState.Uninitialized -> {
            Text("Uninitialized")
        }

        is DataState.Error -> {
            Text("Error")
        }
        DataState.Loading -> {
            Text("Loading")
        }
        is DataState.Success -> {
            Text(text = assetState.toString())
        }
    }
}
