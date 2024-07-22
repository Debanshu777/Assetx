package com.debanshudatta.fintrack.android.views.stockScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.debanshudatta.fintrack.android.views.common.organism.DynamicTapNavigationOrganism

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StockScreen(

) {
    val tabsList: List<Pair<String, @Composable () -> Unit>> = listOf(
        Pair("Explore") { StockExploreView() },
        Pair("Holdings") { StockHoldingView() }
    )
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabsList.size },
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        DynamicTapNavigationOrganism(
            tabsList,
            pagerState
        )
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
        ) { page ->
            tabsList[page].second()
        }
    }
}