package com.codespacepro.weathercomposeapp.navigation.screens

import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codespacepro.weathercomposeapp.model.Weather
import com.codespacepro.weathercomposeapp.repository.Repository
import com.codespacepro.weathercomposeapp.util.Constant
import com.codespacepro.weathercomposeapp.viewmodels.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen() {

    val repository = Repository()
    val mainViewModel = MainViewModel(repository = repository)
    val owner: LifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    var data by remember {
        mutableStateOf<Weather?>(null)
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
    } catch (e: Exception) {
        Log.d("MainScreen", " ${e.printStackTrace()}")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0XFF1691c9))
    )
    {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = "",
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
                    imageVector = Icons.Default.MoreVert, contentDescription = "",
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
        )
        {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = context)
                    .data("https:" + data?.current?.condition?.icon)
                    .crossfade(enable = true)
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
                    8.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                Text(
                    text = "${if (data?.current?.is_day == 0) "Night" else "Day"}",
                    color = Color.White,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "|",
                    color = Color.White,
                )
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
                modifier = Modifier.fillMaxWidth(0.95f),
                color = Color.White,
                thickness = 2.dp
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
                                imageVector = Icons.Default.Send, contentDescription = "",
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
                                imageVector = Icons.Default.Send, contentDescription = "",
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
                                imageVector = Icons.Default.Send, contentDescription = "",
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
                                imageVector = Icons.Default.Send, contentDescription = "",
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
}

fun convertDate(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val outputFormat = SimpleDateFormat(" MMM d", Locale.getDefault())
    val inputDate: Date = inputFormat.parse(date)
    return outputFormat.format(inputDate)
}


