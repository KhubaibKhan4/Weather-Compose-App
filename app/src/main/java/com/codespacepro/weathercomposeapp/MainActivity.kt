package com.codespacepro.weathercomposeapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codespacepro.weathercomposeapp.model.Weather
import com.codespacepro.weathercomposeapp.navigation.screens.WeatherLocation
import com.codespacepro.weathercomposeapp.repository.Repository
import com.codespacepro.weathercomposeapp.ui.theme.WeatherComposeAppTheme
import com.codespacepro.weathercomposeapp.viewmodels.viewmodel.MainViewModel
import com.codespacepro.weathercomposeapp.viewmodels.viewmodelfactory.MainViewModelFactory

class MainActivity : ComponentActivity() {
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            var isLoading by remember {
                mutableStateOf(true)
            }
            val context = LocalContext.current
            var weather by remember {
                mutableStateOf<Weather?>(null)
            }
            val repository = Repository()
            val mainViewModelFactory = MainViewModelFactory(repository)
            mainViewModel =
                ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

            mainViewModel.getWeather("ddecfcfe887f4132882190209230309", "multan")
            mainViewModel.myResponse.observe(this, Observer { response ->
                if (response.isSuccessful) {

                    val location = response.body()?.location
                    if (location != null) {
                        weather = Weather(response.body()!!.current, location)
                        isLoading = false
                    } else {
                        isLoading = false
                        Toast.makeText(context, response.code().toString(), Toast.LENGTH_SHORT)
                            .show()
                    }

                    Log.d("main", response.body()?.location!!.country)
                    Log.d("main", response.body()?.location!!.name)
                    Log.d("main", response.body()?.location!!.region)
                    Log.d("main", response.body()?.location!!.localtime)
                    Log.d("main", response.body()?.location!!.localtime_epoch.toString())
                    Log.d("main", response.body()?.location!!.lat.toString())
                    Log.d("main", response.body()?.location!!.lon.toString())
                } else {
                    Toast.makeText(context, response.errorBody().toString(), Toast.LENGTH_SHORT)
                        .show()
                }

            })

            WeatherComposeAppTheme {

                if (isLoading) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(40.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 4.dp
                    )


                } else if (weather != null) {
                    weather?.let { WeatherLocation(weather = it) }
                } else {
                    Toast.makeText(context, "Failed to load Data..", Toast.LENGTH_SHORT)
                        .show()
                }
              //Bottom Navigation
//                MainScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherComposeAppTheme {
        Greeting("Android")
    }
}