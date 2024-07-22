package com.debanshudatta.fintrack.android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.debanshudatta.fintrack.android.views.common.organism.BottomNavigationTabOrganism
import com.debanshudatta.fintrack.android.views.common.model.bottomNavigationItems
import com.debanshudatta.fintrack.android.views.common.model.homeTab
import com.debanshudatta.fintrack.android.views.common.model.stockTab


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationTabOrganism(
                            bottomNavigationItems,
                            navController
                        )
                    },
                    ) {
                    NavHost(
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