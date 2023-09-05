package com.codespacepro.weathercomposeapp.navigation.navgraph.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codespacepro.weathercomposeapp.model.Condition
import com.codespacepro.weathercomposeapp.model.Current
import com.codespacepro.weathercomposeapp.model.Location
import com.codespacepro.weathercomposeapp.navigation.navgraph.screen.BottomNavScreen
import com.codespacepro.weathercomposeapp.navigation.screens.DetailScreen
import com.codespacepro.weathercomposeapp.navigation.screens.SearchScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {

    var location = Location(
        country = "United States",
        lat = 37.7749,
        localtime = "2023-09-06 14:30:00",
        localtime_epoch = 1630931400,
        lon = -122.4194,
        name = "San Francisco",
        region = "California",
        tz_id = "America/Los_Angeles",
    )
    var condition = Condition(
        code = 800,
        icon = "01d",
        text = "Clear Sky"
    )
    var current = Current(
        cloud = 50,
        condition = condition,
        feelslike_c = 22.5,
        feelslike_f = 72.5,
        gust_kph = 15.2,
        gust_mph = 9.4,
        humidity = 60,
        is_day = 1,
        last_updated = "2023-09-06 14:30:00",
        last_updated_epoch = 1630931400,
        precip_in = 0.0,
        precip_mm = 0.0,
        pressure_in = 29.92,
        pressure_mb = 1013.25,
        temp_c = 23.8,
        temp_f = 74.8,
        uv = 5.8,
        vis_km = 10.0,
        vis_miles = 6.2,
        wind_degree = 150,
        wind_dir = "SE",
        wind_kph = 12.0,
        wind_mph = 7.5
    )

    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.WeatherLocation.route
    ) {
        composable(route = BottomNavScreen.WeatherLocation.route) {
            //WeatherLocation(weather = Weather(current = current, location = location))
        }
        composable(route = BottomNavScreen.SearchScreen.route) {
            SearchScreen()
        }
        composable(route = BottomNavScreen.DetailScreen.route) {
            DetailScreen()
        }
    }
}