package com.debanshudatta.fintrack.android.views.stockScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.debanshudatta.fintrack.android.views.common.organism.DynamicTapNavigationOrganism
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StockScreen() {
    val tabsList: List<Pair<String, @Composable () -> Unit>> = listOf(
        Pair("Explore") { StockExploreView() },
        Pair("Holdings") { StockHoldingView() }
    )
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabsList.size },
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        DynamicTapNavigationOrganism(
            tabsList = tabsList.map { it.first }.toList(),
            currentPage = selectedTabIndex
        ) { tabIndex ->
            selectedTabIndex = tabIndex
            coroutineScope.launch {
                pagerState.animateScrollToPage(tabIndex)
            }
        }
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
        ) { page ->
            tabsList[page].second()
        }
    }
}
