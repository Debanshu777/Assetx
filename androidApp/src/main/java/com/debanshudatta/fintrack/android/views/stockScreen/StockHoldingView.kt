package com.debanshudatta.fintrack.android.views.stockScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Aggregator View
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column{
                                Text("Current")
                                Text(
                                    "₹${aggregator.currentTotalValue.roundTo()}",

                                )
                            }
                            Column{
                                Text("Total returns", )
                                Text(
                                    "+₹${(aggregator.currentTotalValue - aggregator.totalInvested).roundTo()} (${aggregator.totalReturnsPercentage.roundTo()}%)",

                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Invested",)
                                Text(
                                    "₹${aggregator.totalInvested.roundTo()}",

                                )
                            }
                            Column{
                                Text("1D returns", )
                                Text(
                                    "+₹2,331 (1.85%)",

                                )
                            }
                        }
                    }
                }

                // Listing View
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    assets.forEach { asset ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
//                            elevation = 2.dp
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(5.dp),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(asset.name)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        "${asset.quantity} shares",
                                    )
                                    val profitValue =
                                        (asset.marketValue - asset.averagePurchasedValue) * asset.quantity
                                    val profitPercentage =
                                        ((asset.marketValue - asset.averagePurchasedValue) / asset.averagePurchasedValue) * 100
                                    Text(
                                        "+₹${profitValue.roundTo()}",
                                    )
                                    Text(
                                        "(${profitPercentage.roundTo()}%)",
                                    )
                                }
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
    val totalReturnsPercentage = if (totalInvested > 0) (totalReturns / totalInvested) * 100 else 0.0

    return AssetAggregator(
        currentTotalValue = currentTotalValue,
        totalInvested = totalInvested,
        totalReturnsPercentage = totalReturnsPercentage
    )
}

data class AssetAggregator(
    val currentTotalValue: Double,
    val totalInvested: Double,
    val totalReturnsPercentage: Double
)
