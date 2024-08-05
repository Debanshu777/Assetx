package com.debanshudatta.fintrack.android.views.stockScreen.organism

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.debanshudatta.fintrack.android.views.stockScreen.molecule.IndicesListItem
import com.debanshudatta.fintrack.data.DataState
import com.debanshudatta.fintrack.data.domain.model.Indices

@Composable
fun GridIndicesView (
    indicesState: State<DataState<Indices>>
){
    Column {
        Text(
            "Market and sectors",
            modifier = Modifier.padding(horizontal = 10.dp),
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(15.dp))
        when (indicesState.value) {
            is DataState.Error -> Text("Error")
            is DataState.Loading -> {}
            is DataState.Success -> {
                val indicesList = (indicesState.value as DataState.Success).stocks
                LazyHorizontalGrid(
                    modifier = Modifier.height(150.dp),
                    rows = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    itemsIndexed(indicesList) { index, indices ->
                        IndicesListItem(indices, isLastItem = index == indicesList.size - 1)
                    }
                }
            }

            is DataState.Uninitialized -> Text("Uninitialized")
        }
    }
}

