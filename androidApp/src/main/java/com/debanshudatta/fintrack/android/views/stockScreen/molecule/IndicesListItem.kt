package com.debanshudatta.fintrack.android.views.stockScreen.molecule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.debanshudatta.fintrack.android.views.common.extension.roundTo
import com.debanshudatta.fintrack.android.views.stockScreen.organism.PerformanceChart
import com.debanshudatta.fintrack.data.model.Indices
import kotlin.math.absoluteValue

@Composable
internal fun IndicesListItem(indices: Indices, isLastItem: Boolean) {
    Card(
        modifier = Modifier
            .padding(
                start = 10.dp,
                end = if (isLastItem) 10.dp else 0.dp
            )
            .height(100.dp)
            .width(350.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(indices.name ?: "Null")
            Spacer(modifier = Modifier.height(20.dp))
            indices.points.map { it.price.toFloat() }
                .let {
                    PerformanceChart(
                        modifier = Modifier.size(80.dp),
                        list = it.toList()
                    )
                }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text("INR ${indices.points.last().price.roundTo()}")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        modifier = Modifier.size(15.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                if (indices.points.last().price - indices.lastClosePrice >= 0)
                                    "https://assets.tickertape.in/images/landing-page/52w_high.svg"
                                else
                                    "https://assets.tickertape.in/images/landing-page/52w_low.svg"
                            )
                            .decoderFactory(SvgDecoder.Factory())
                            .build(),
                        contentDescription = "low"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "${(indices.points.last().price - indices.lastClosePrice).roundTo().absoluteValue}%",
                        color = if (indices.points.last().price - indices.lastClosePrice >= 0) Color(
                            "#19af55".toColorInt()
                        ) else Color(
                            "#d82f44".toColorInt()
                        )
                    )
                }
            }
        }
    }
}
