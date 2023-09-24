package com.codespacepro.weathercomposeapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codespacepro.weathercomposeapp.model.Weather
import com.codespacepro.weathercomposeapp.navigation.screens.WeatherLocation
import com.codespacepro.weathercomposeapp.repository.Repository
import com.codespacepro.weathercomposeapp.ui.theme.WeatherComposeAppTheme
import com.codespacepro.weathercomposeapp.util.Constant.Companion.apiKey
import com.codespacepro.weathercomposeapp.viewmodels.viewmodel.MainViewModel
import com.codespacepro.weathercomposeapp.viewmodels.viewmodelfactory.MainViewModelFactory

class MainActivity : ComponentActivity() {
    lateinit var mainViewModel: MainViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var searchText by rememberSaveable {
                mutableStateOf("multan")
            }

            var isSearchVisible by rememberSaveable {
                mutableStateOf(false)
            }


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

            mainViewModel.getWeather(apiKey, searchText)
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

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 4.dp
                        )
                    }


                } else if (weather != null) {
                    Column {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.Black),
                            value = searchText,
                            onValueChange = { searchText = it },
                            trailingIcon = {
                                IconButton(onClick = {
                                    mainViewModel.getWeather(
                                        apiKey,
                                        searchText
                                    )
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = null
                                    )
                                }
                            },
                            label = { Text(text = "Search") },
                            placeholder = { Text(text = "Search Location...") },
                            interactionSource = MutableInteractionSource(),
                            maxLines = 1,
                            colors = TextFieldDefaults.colors(
                                cursorColor = Color.White,
                                focusedContainerColor = Color.Black,
                                focusedTextColor = Color.White,
                                unfocusedContainerColor = Color.Black,
                                unfocusedLabelColor = Color.White,
                                unfocusedIndicatorColor = Color.White,
                                unfocusedPlaceholderColor = Color.White,
                                unfocusedTextColor = Color.White,
                                unfocusedTrailingIconColor = Color.White,
                                unfocusedLeadingIconColor = Color.White,
                                focusedTrailingIconColor = Color.White
                            ),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Characters,
                                autoCorrect = true,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Search
                            ),
                            keyboardActions = KeyboardActions(onSearch = {
                                mainViewModel.getWeather(
                                    apiKey,
                                    searchText
                                )
                            })

                        )
                        weather?.let { WeatherLocation(weather = it) }
                    }
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
