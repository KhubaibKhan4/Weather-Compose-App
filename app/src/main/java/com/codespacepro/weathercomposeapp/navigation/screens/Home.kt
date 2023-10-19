package com.codespacepro.weathercomposeapp.navigation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codespacepro.weathercomposeapp.R
import com.codespacepro.weathercomposeapp.component.ForecastList
import com.codespacepro.weathercomposeapp.model.Weather
import com.codespacepro.weathercomposeapp.repository.Repository
import com.codespacepro.weathercomposeapp.util.Constant
import com.codespacepro.weathercomposeapp.util.Constant.Companion.API_KEY
import com.codespacepro.weathercomposeapp.viewmodels.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val repository = Repository()
    val mainViewModel = MainViewModel(repository = repository)
    val owner: LifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    var data by remember {
        mutableStateOf<Weather?>(null)
    }
    var foreCast by remember {
        mutableStateOf<Weather?>(null)
    }
    var searchInput by remember {
        mutableStateOf("")
    }
    var isVisible by remember {
        mutableStateOf(false)
    }
    try {
        mainViewModel.getWeather(api = Constant.API_KEY, q = "multan")
        mainViewModel.myResponse.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                data = response.body()
                Log.d("MainScreen", " ${response.body()}")

            } else {
                Log.d("MainScreen", " ${response.code()}")
            }
        })

        mainViewModel.getForecast(api = Constant.API_KEY, q = "multan")
        mainViewModel.myForecastResponse.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                foreCast = response.body()
                Log.d("MainScreen", " ${response.body()}")

            } else {
                Log.d("MainScreen", " ${response.code()}")
            }
        })
    } catch (e: Exception) {
        Log.d("MainScreen", " ${e.printStackTrace()}")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0XFF1691c9))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                isVisible = !isVisible
            }) {
                Icon(
                    imageVector = if (isVisible) Icons.Default.Close else Icons.Default.Search,
                    contentDescription = "",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${data?.location?.name ?: "Multan"}",
                color = Color.White,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkHorizontally()
            ) {
                TextField(
                    value = searchInput,
                    onValueChange = { searchInput = it },
                    enabled = isVisible,
                    label = { Text(text = "Location") },
                    placeholder = { Text(text = "Search Location...") },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Gray,
                        unfocusedContainerColor = Color.LightGray,
                        focusedContainerColor = Color.White,
                    ),
                    trailingIcon = {
                        IconButton(onClick = {
                            mainViewModel.getWeather(api = API_KEY, q = searchInput)
                        }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "")
                        }
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrect = true,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(onSearch = {
                        mainViewModel.getWeather(api = API_KEY, q = searchInput)
                    }),
                    modifier = Modifier
                        .fillMaxWidth(0.80f)
                        .clip(shape = RoundedCornerShape(24.dp))
                )
            }
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data("https:" + data?.current?.condition?.icon).crossfade(enable = true)
                    .build(),
                contentDescription = "Null",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(170.dp),
                colorFilter = ColorFilter.tint(color = Color.White)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    8.dp, alignment = Alignment.CenterHorizontally
                )
            ) {
                Text(
                    text = "${data?.current?.last_updated?.let { convertDate(it) }}",
                    color = Color.White,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "${data?.current?.feelslike_c}°",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 56.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${data?.current?.condition?.text}",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light,
                color = Color.White,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                modifier = Modifier.fillMaxWidth(0.95f), color = Color.White, thickness = 2.dp
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.wind_power),
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "${data?.current?.wind_kph} km/h Wind",
                                textAlign = TextAlign.Center,
                                color = Color.White,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.cloud),
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "${data?.current?.cloud}% Chances of Rain",
                                color = Color.White,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.pressure),
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "${data?.current?.pressure_mb} mbar",
                                textAlign = TextAlign.Center,
                                color = Color.White,
                            )
                        }
                    }
                    Box {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.humidity),
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "${data?.current?.humidity}%  Humidity",
                                textAlign = TextAlign.Center,
                                color = Color.White,
                            )
                        }
                    }
                }
            }

        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 520.dp)
            .background(color = Color(0XFF1691c9))
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        data?.current?.last_updated?.let { lastUpdated ->
            Text(
                text = convertDate(lastUpdated) ?: Date().toString(),
                color = Color.White,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                textAlign = TextAlign.Start
            )
        }
        foreCast?.forecast?.forecastday?.forEach { forecastDay ->
            ForecastList(weather = forecastDay.hour)
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(24.dp))
                .background(color = Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Forecast for 7 Days ",
                color = Color(0XFF1691c9),
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = {
                Toast.makeText(
                    context,
                    "Api Subscription is require for 7 Days Forecast.",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown, contentDescription = "",
                    tint = Color(0XFF1691c9)
                )
            }
        }
    }

}

fun convertDate(date: String): String {
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMM d", Locale.getDefault())
    val dateObject = inputDateFormat.parse(date)
    val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    val dayName = dayFormat.format(dateObject)
    val monthName = outputFormat.format(dateObject)
    return "$dayName | $monthName"
}




