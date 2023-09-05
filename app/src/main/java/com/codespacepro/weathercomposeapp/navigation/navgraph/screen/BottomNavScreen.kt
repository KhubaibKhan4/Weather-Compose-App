package com.codespacepro.weathercomposeapp.navigation.navgraph.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object WeatherLocation : BottomNavScreen(
        route = "weather",
        title = "Home",
        icon = Icons.Filled.Home
    )

    object SearchScreen : BottomNavScreen(
        route = "search",
        title = "Search",
        icon = Icons.Filled.Search
    )

    object DetailScreen: BottomNavScreen(
        route = "detail",
        title = "Details",
        icon = Icons.Filled.Info
    )

}
