package com.debanshudatta.fintrack.android.views.common.organism

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DynamicTapNavigationOrganism(
    tabsList: List<Pair<String,@Composable ()->Unit>>,
    pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        edgePadding = 0.dp,
        divider = {},
        indicator = {}
    ) {
        tabsList.forEachIndexed { tabIndex, tabName ->
            FilterChip(
                modifier = Modifier
                    .wrapContentSize(),
                selected = tabIndex == pagerState.currentPage,
                border = null,
                onClick = { coroutineScope.launch { pagerState.animateScrollToPage(tabIndex) } },
                label = {
                    Text(text = tabName.first, textAlign = TextAlign.Center)
                }
            )
        }
    }
}