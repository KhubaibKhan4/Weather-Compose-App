package com.codespacepro.weathercomposeapp.navigation.navgraph.navgraph


import SettingScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codespacepro.weathercomposeapp.navigation.navgraph.screen.BottomScreen
import com.codespacepro.weathercomposeapp.navigation.screens.AboutScreen
import com.codespacepro.weathercomposeapp.navigation.screens.HomeScreen
import com.codespacepro.weathercomposeapp.navigation.screens.WeatherLocation

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomScreen.Home.route) {
        composable(route = BottomScreen.Home.route) {
            HomeScreen(navController)
        }

        composable(route = BottomScreen.Weather.route) {
            WeatherLocation()
        }
        composable(BottomScreen.About.route) {
            AboutScreen()
        }

        composable(BottomScreen.Setting.route) {
            SettingScreen(navController)
        }
    }

}