package com.debanshudatta.fintrack.android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.debanshudatta.fintrack.android.views.common.organism.BottomNavigationTabOrganism
import com.debanshudatta.fintrack.android.views.common.model.bottomNavigationItems
import com.debanshudatta.fintrack.android.views.common.model.homeTab
import com.debanshudatta.fintrack.android.views.common.model.stockTab


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        MediumTopAppBar(
                            title = {
                                Column {
                                    Text(stockTab.title)
                                    TextField(
                                        value = "",
                                        onValueChange = {},
                                        label = { Text("Search") },
                                        modifier = Modifier.fillMaxWidth(),
                                    )
                                }
                            },
                            scrollBehavior = scrollBehavior,
                            collapsedHeight = 0.dp
                        )
                    },
                    bottomBar = {
                        BottomNavigationTabOrganism(
                            bottomNavigationItems,
                            navController
                        )
                    },
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = stockTab.title,
                    ) {
                        composable(stockTab.title) {
                            stockTab.screen()
                        }
                        composable(homeTab.title) {
                            homeTab.screen()
                        }
                    }
                }

            }
        }
    }
}
