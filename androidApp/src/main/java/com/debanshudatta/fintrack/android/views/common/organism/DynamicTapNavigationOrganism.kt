package com.debanshudatta.fintrack.android.views.common.organism

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kotlinx.coroutines.launch

@Composable
fun DynamicTapNavigationOrganism(
    tabsList: List<String>,
    iconList: List<String> = emptyList(),
    currentPage: Int,
    onTapChangeCallback: (Int) -> Unit = {},
) {
    ScrollableTabRow(
        selectedTabIndex = currentPage,
        edgePadding = 0.dp,
        divider = {},
        indicator = {}
    ) {
        tabsList.forEachIndexed { tabIndex, tabName ->
            FilterChip(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .wrapContentSize()
                    .clip(RoundedCornerShape(10.dp)),
                selected = tabIndex == currentPage,
                border = BorderStroke(0.5.dp, Color.LightGray),
                onClick = {
                    onTapChangeCallback(tabIndex)
                },
                leadingIcon = {
                    if (iconList.isNotEmpty()) {
                        AsyncImage(
                            modifier = Modifier.size(20.dp),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(iconList[tabIndex])
                                .decoderFactory(SvgDecoder.Factory())
                                .build(),
                            contentDescription = tabName
                        )
                    }
                },
                label = {
                    Text(text = tabName, textAlign = TextAlign.Center)
                }
            )
        }
    }
}