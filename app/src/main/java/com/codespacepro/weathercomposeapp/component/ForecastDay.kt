package com.codespacepro.weathercomposeapp.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codespacepro.weathercomposeapp.model.Hour
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ForecastList(weather: List<Hour>) {
    LazyRow {
        items(weather) { hour ->
            ForecastDayItem(hour = hour)
        }
    }
}

@Composable
fun ForecastDayItem(hour: Hour) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${convertDate(hour.time)}",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(6.dp))
        // Display weather icon
        AsyncImage(
            model = ImageRequest.Builder(context = context)
                .data("https:" + hour.condition.icon).crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${hour.temp_c}°/${hour.temp_f}°",
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${hour.chance_of_rain}% rain",
            color = Color.White
        )
    }
}

fun convertDate(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val dateObject = inputFormat.parse(date)
    val time = outputFormat.format(dateObject)
    return "$time"
}
