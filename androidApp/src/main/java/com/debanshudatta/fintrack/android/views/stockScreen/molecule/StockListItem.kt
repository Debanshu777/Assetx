package com.debanshudatta.fintrack.android.views.stockScreen.molecule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.debanshudatta.fintrack.android.views.common.extension.roundTo
import com.debanshudatta.fintrack.android.views.common.extension.shimmerBrush
import com.debanshudatta.fintrack.data.model.Stock
import kotlin.math.absoluteValue

@Composable
fun StockListItem(stock: Stock?, isLoading: Boolean, isLastItem: Boolean) {
    val painter = rememberVectorPainter(image = Icons.Filled.BrokenImage)
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    model = if (!isLoading) "https://assets.tickertape.in/stock-logos/${stock!!.sid}.png" else null,
                    contentDescription = if (isLoading) "" else stock!!.name,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            shimmerBrush(
                                targetValue = 1300f,
                                showShimmer = isLoading
                            )
                        ),
                    error = if (!isLoading) painter else null
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        if (isLoading) "" else stock!!.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .widthIn(min = 250.dp, max = 250.dp)
                            .background(
                                shimmerBrush(
                                    targetValue = 1300f,
                                    showShimmer = isLoading
                                )
                            ),
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        if (isLoading) "" else stock!!.ticker,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray,
                        modifier = Modifier
                            .widthIn(min = 210.dp)
                            .background(
                                shimmerBrush(
                                    targetValue = 1300f,
                                    showShimmer = isLoading
                                )
                            ),
                    )
                }
            }
            Column(
                horizontalAlignment = AbsoluteAlignment.Right
            ) {
                Text(
                    text = if (isLoading) "" else "INR ${stock!!.price.roundTo()}",
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .widthIn(min = 100.dp)
                        .background(
                            shimmerBrush(
                                targetValue = 1300f,
                                showShimmer = isLoading
                            )
                        ),
                )
                Spacer(modifier = Modifier.width(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        modifier = Modifier
                            .size(15.dp)
                            .background(
                                shimmerBrush(
                                    targetValue = 1300f,
                                    showShimmer = isLoading
                                )
                            ),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                if (!isLoading) {
                                    if (stock!!.change >= 0)
                                        "https://assets.tickertape.in/images/landing-page/52w_high.svg"
                                    else
                                        "https://assets.tickertape.in/images/landing-page/52w_low.svg"
                                } else null
                            )
                            .decoderFactory(SvgDecoder.Factory())
                            .build(),
                        contentDescription = "low"
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        if (isLoading) "" else "${stock!!.change.roundTo().absoluteValue}%",
                        color = if (!isLoading && stock!!.change >= 0) Color("#19af55".toColorInt()) else Color(
                            "#d82f44".toColorInt()
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .widthIn(min = 15.dp)
                            .background(
                                shimmerBrush(
                                    targetValue = 1300f,
                                    showShimmer = isLoading
                                )
                            ),
                    )
                }
            }
        }
        if (isLastItem) {
            HorizontalDivider(
                thickness = 0.5.dp,
                modifier = Modifier.padding(horizontal = 15.dp),
                color = Color.LightGray
            )
        }
    }
}
