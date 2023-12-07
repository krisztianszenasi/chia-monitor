package com.example.chiamonitor.presentation.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController


@Composable
fun BottomNavigation(navController: NavController) {

    val items = listOf(
        BottomNavItem.BlockchainState,
        BottomNavItem.BackupScreen,
        BottomNavItem.ServicesScreen,
        BottomNavItem.ConnectionSettings,
    )

    var selected by remember { mutableStateOf<BottomNavItem>(BottomNavItem.ConnectionSettings) }

    NavigationBar {
        items.forEach { item ->
            AddItem(
                screen = item,
                selected = item == selected,
                onClick = {
                    selected = item
                    navController.navigate(item.route)
                },
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavItem,
    selected: Boolean,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        // Text that shows bellow the icon
        label = {
            Text(text = screen.title)
        },

        // The icon resource
        icon = {
            Icon(
                painterResource(id = screen.icon),
                contentDescription = screen.title
            )
        },

        // Display if the icon it is select or not
        selected = selected,

        // Always show the label bellow the icon or not
        alwaysShowLabel = false,

        // Click listener for the icon
        onClick = { onClick() },

        // Control all the colors of the icon
        colors = NavigationBarItemDefaults.colors()
    )
}