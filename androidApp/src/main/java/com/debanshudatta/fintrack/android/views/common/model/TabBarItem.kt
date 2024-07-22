package com.debanshudatta.fintrack.android.views.common.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.debanshudatta.fintrack.android.views.homeScreen.HomeScreen
import com.debanshudatta.fintrack.android.views.stockScreen.StockExploreView
import com.debanshudatta.fintrack.android.views.stockScreen.StockHoldingView
import com.debanshudatta.fintrack.android.views.stockScreen.StockScreen

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val notSelectedIcon: ImageVector,
    val screen: @Composable () -> Unit,
)

val stockTab = TabBarItem(
    "Stock",
    selectedIcon = Icons.Filled.PlayArrow,
    notSelectedIcon = Icons.Outlined.PlayArrow,
) {
    StockScreen()
}

val homeTab = TabBarItem(
    "Home",
    selectedIcon = Icons.Filled.Home,
    notSelectedIcon = Icons.Outlined.Home,
    screen = { HomeScreen() }
)

val bottomNavigationItems = listOf(
    stockTab,
    homeTab,
)
