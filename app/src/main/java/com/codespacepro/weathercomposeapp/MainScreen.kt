package com.codespacepro.weathercomposeapp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.codespacepro.weathercomposeapp.navigation.navgraph.navgraph.SetupNavGraph
import com.codespacepro.weathercomposeapp.navigation.navgraph.screen.BottomScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomBar(navController = navController) }, content = {
        SetupNavGraph(navController = navController)
    })

}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomScreen.Home,
        BottomScreen.Weather,
        BottomScreen.Search
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        if (currentDestination != null) {
            screens.forEach { screens ->
                AddItems(
                    screen = screens,
                    navDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItems(
    screen: BottomScreen,
    navDestination: NavDestination,
    navController: NavHostController
) {
    NavigationBarItem(
        selected = navDestination.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.id)
                launchSingleTop = true
            }
        },
        icon = { Icon(imageVector = screen.icon, contentDescription = "") },
        label = { Text(text = screen.title) }
    )
}