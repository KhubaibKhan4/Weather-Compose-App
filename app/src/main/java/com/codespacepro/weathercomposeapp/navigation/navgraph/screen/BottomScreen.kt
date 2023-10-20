package com.codespacepro.weathercomposeapp.navigation.navgraph.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomScreen(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    object Weather: BottomScreen(
        route = "weather",
        title = "Weather",
        icon = Icons.Default.MailOutline
    )
    object About: BottomScreen(
        route = "about",
        title = "About",
        icon = Icons.Default.AccountBox
    )

    object Setting: BottomScreen(
        route = "setting",
        title = "Setting",
        icon = Icons.Default.Settings
    )
}
