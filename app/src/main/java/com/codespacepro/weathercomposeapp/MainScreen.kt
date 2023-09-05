package com.codespacepro.weathercomposeapp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.codespacepro.weathercomposeapp.navigation.navgraph.navgraph.BottomNavGraph
import com.codespacepro.weathercomposeapp.navigation.navgraph.screen.BottomNavScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        BottomBar(navController = navController)
    },
        content = {
            BottomNavGraph(navController = navController)
        })


}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomNavScreen.WeatherLocation,
        BottomNavScreen.SearchScreen,
        BottomNavScreen.DetailScreen
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screens ->
            if (currentDestination != null) {
                AddItem(
                    screens = screens,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screens: BottomNavScreen,
    currentDestination: NavDestination,
    navController: NavHostController
) {

    NavigationBarItem(
        selected = currentDestination.hierarchy?.any {
            it.route == screens.route
        } == true,
        onClick = {
            navController.navigate(screens.route) {
                popUpTo(screens.route)
                launchSingleTop = true
            }
        },
        icon = { Icon(imageVector = screens.icon, contentDescription = "Icons") },
        label = {
            Text(text = screens.title)
        }
    )

}