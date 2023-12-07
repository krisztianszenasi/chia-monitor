package com.example.chiamonitor.presentation.navigation

import androidx.annotation.DrawableRes
import com.example.chiamonitor.R


sealed class BottomNavItem(
    var title: String,
    @DrawableRes var icon: Int,
    var route: String
) {
    object BlockchainState : BottomNavItem(
        title = "state",
        icon = R.drawable.baseline_public_light,
        route = "blockchain_state",
    )

    object BackupScreen : BottomNavItem(
        title = "backup",
        icon = R.drawable.baseline_sd_storage_24_light,
        route = "backup_screen",
    )

    object ServicesScreen : BottomNavItem(
        title = "services",
        icon = R.drawable.baseline_electrical_services_24,
        route = "services_screen",
    )

    object ConnectionSettings : BottomNavItem(
        title = "settings",
        icon = R.drawable.baseline_settings_24_light,
        route = "connection_settings",
    )
}
