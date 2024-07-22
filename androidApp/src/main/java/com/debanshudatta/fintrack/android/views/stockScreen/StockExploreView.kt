package com.debanshudatta.fintrack.android.views.stockScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.debanshudatta.fintrack.data.AppViewModel
import com.debanshudatta.fintrack.data.DataState
import org.koin.androidx.compose.koinViewModel

@Composable
fun StockExploreView() {
    val viewModel: AppViewModel = koinViewModel()
    val universeList = viewModel.universeDataList.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (universeList.value) {
            is DataState.Error -> Text("Error")
            DataState.Loading -> CircularProgressIndicator()
            is DataState.Success -> Column {
                Button(onClick = { viewModel.stopPolling() }) { Text("Stop") }
                Text(universeList.value.toString())
            }
            DataState.Uninitialized -> CircularProgressIndicator()
        }
    }
}
