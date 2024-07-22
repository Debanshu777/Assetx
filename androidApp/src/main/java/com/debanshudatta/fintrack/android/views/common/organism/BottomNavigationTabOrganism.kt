package com.debanshudatta.fintrack.android.views.common.organism

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.debanshudatta.fintrack.android.views.common.model.TabBarItem

@Composable
fun BottomNavigationTabOrganism(
    tabBarItems: List<TabBarItem>,
    navController: NavController,
    ) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar {
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    navController.navigate(tabBarItem.title)
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedTabIndex) {
                            tabBarItem.selectedIcon
                        } else {
                            tabBarItem.notSelectedIcon
                        },
                        contentDescription = tabBarItem.title
                    )
                },
                label = { Text(tabBarItem.title) })
        }
    }
}