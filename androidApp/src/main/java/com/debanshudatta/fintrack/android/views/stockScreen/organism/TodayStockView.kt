package com.debanshudatta.fintrack.android.views.stockScreen.organism

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.debanshudatta.fintrack.android.views.common.model.ChipItem
import com.debanshudatta.fintrack.android.views.common.organism.DynamicTapNavigationOrganism
import com.debanshudatta.fintrack.android.views.stockScreen.molecule.StockListItem
import com.debanshudatta.fintrack.data.DataState
import com.debanshudatta.fintrack.data.domain.model.Stock
import com.debanshudatta.fintrack.data.domain.model.Type

@Composable
fun TodayStockView(
    universeState: State<DataState<Stock>>,
    setStockType: (Type) -> Unit,
) {
    val tabsList: List<Pair<ChipItem, Type>> = listOf(
        Pair(
            ChipItem("Gainers", "https://assets.tickertape.in/images/landing-page/gainers.svg"),
            Type.gainers
        ),
        Pair(
            ChipItem("Losers", "https://assets.tickertape.in/images/landing-page/losers.svg"),
            Type.losers
        ),
        Pair(
            ChipItem(
                "Most Active",
                "https://assets.tickertape.in/images/landing-page/most_active.svg"
            ), Type.active
        ),
        Pair(
            ChipItem("52W High", "https://assets.tickertape.in/images/landing-page/52w_high.svg"),
            Type.approachingHigh
        ),
        Pair(
            ChipItem("52W Low", "https://assets.tickertape.in/images/landing-page/52w_low.svg"),
            Type.approachingLow
        ),
    )
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    Column {
        Text(
            "Today's stocks",
            modifier = Modifier.padding(horizontal = 10.dp),
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(15.dp))
        DynamicTapNavigationOrganism(
            tabsList = tabsList.map { it.first.name }.toList(),
            iconList = tabsList.map { it.first.iconUrl }.toList(),
            currentPage = selectedTabIndex
        ) { tabIndex ->
            selectedTabIndex = tabIndex
            setStockType(tabsList[tabIndex].second)
        }
        when (universeState.value) {
            is DataState.Error -> Text("Error")
            is DataState.Loading -> {
                Column {
                    (0..5).forEach { _ ->
                        StockListItem(null, true,false)
                    }
                }
            }
            is DataState.Success -> {
                val stocks = (universeState.value as DataState.Success).stocks
                LazyColumn(
                    modifier = Modifier
                        .height(400.dp)
                        .padding(horizontal = 10.dp),
                    userScrollEnabled = false
                ) {
                    itemsIndexed(stocks) { index, stock ->
                        StockListItem(stock, false, index < stocks.size - 1)
                    }
                }
            }
            DataState.Uninitialized -> {
                Column {
                    (0..5).forEach { _ ->
                        StockListItem(null, true,false)
                    }
                }
            }
        }
    }
}
