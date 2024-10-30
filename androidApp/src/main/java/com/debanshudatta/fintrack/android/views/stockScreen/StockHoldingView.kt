package com.debanshudatta.fintrack.android.views.stockScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.debanshudatta.fintrack.AppViewModel
import com.debanshudatta.fintrack.DataState
import com.debanshudatta.fintrack.android.views.common.extension.roundTo
import com.debanshudatta.fintrack.entities.AssetEntity
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
            val assets = (assetState.value as DataState.Success).stocks
            val aggregator = calculateAggregator(assets)

            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                // Aggregator View
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Current", style = MaterialTheme.typography.labelSmall)
                            Text(
                                "₹${aggregator.currentTotalValue.roundTo()}",
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text("Total returns", style = MaterialTheme.typography.labelSmall)
                            Text("+₹${(aggregator.currentTotalValue - aggregator.totalInvested).roundTo()} (${aggregator.totalReturnsPercentage.roundTo()}%)",)
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Invested", style = MaterialTheme.typography.labelSmall)
                            Text("₹${aggregator.totalInvested.roundTo()}",)
                        }
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text("1D returns", style = MaterialTheme.typography.labelSmall)
                            Text("+₹2,331 (1.85%)",)
                        }
                    }
                }

                // Listing View
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(assets) { asset ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(asset.name, fontWeight = FontWeight.Bold)
                                Text(
                                    "${asset.quantity} shares",
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                            val profitValue =
                                (asset.marketValue - asset.averagePurchasedValue) * asset.quantity
                            val profitPercentage =
                                ((asset.marketValue - asset.averagePurchasedValue) / asset.averagePurchasedValue) * 100
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    "+₹${profitValue.roundTo()}",
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                                Text(
                                    "(${profitPercentage.roundTo()}%)",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


private fun calculateAggregator(assets: List<AssetEntity>): AssetAggregator {
    val totalInvested = assets.sumOf { it.averagePurchasedValue * it.quantity }
    val currentTotalValue = assets.sumOf { it.marketValue * it.quantity }
    val totalReturns = currentTotalValue - totalInvested
    val totalReturnsPercentage =
        if (totalInvested > 0) (totalReturns / totalInvested) * 100 else 0.0

    return AssetAggregator(
        currentTotalValue = currentTotalValue,
        totalInvested = totalInvested,
        totalReturnsPercentage = totalReturnsPercentage
    )
}

data class AssetAggregator(
    val currentTotalValue: Double, val totalInvested: Double, val totalReturnsPercentage: Double
)
