package com.codespacepro.weathercomposeapp.navigation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codespacepro.weathercomposeapp.R
import com.codespacepro.weathercomposeapp.model.Weather


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherLocation(weather: Weather) {
    val context = LocalContext.current
    val text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp
            )
        ) {
            append("${weather.location.name} ")
        }
        withStyle(
            style = SpanStyle(
                color = Color.LightGray,
                fontWeight = FontWeight.Medium,

                )
        ) {
            append(
                "${weather.location.region}, " + "${weather.location.country}"
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onSurface)
            .padding(top = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.Center,

        ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(0.75f)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.onSurface)
                    .padding(start = 5.dp, end = 5.dp, top = 8.dp, bottom = 10.dp),
            ) {
                Text(
                    text = "Last Update: ${weather.current.last_updated}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Normal,
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    Image(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Location",
                        colorFilter = ColorFilter.tint(color = Color.White)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = text)
                }

            }
            var visibility by remember {
                mutableStateOf(false)
            }
            IconButton(onClick = { visibility = !visibility }) {

                if (visibility) {
                    Icon(
                        imageVector = Icons.Filled.Close, contentDescription = null,
                        tint = Color.White
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Search, contentDescription = null,
                        tint = Color.White
                    )
                }

            }

        }
        Column {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
                    .padding(top = 140.dp)
                    .paint(
                        painter = painterResource(id = R.drawable.world_map),
//                        colorFilter = ColorFilter.tint(color = Color.LightGray, blendMode = BlendMode.Saturation),
                        alpha = 0.3f
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                AsyncImage(
                    model = ImageRequest
                        .Builder(context = context)
                        .data("https:" + weather.current.condition.icon)
                        .crossfade(enable = true)
                        .build(),
                    contentDescription = "Null",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(50.dp),
                    colorFilter = ColorFilter.tint(color = Color.White)
                )


                Text(
                    text = "${weather.current.condition.text}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Text(
                    text = "${weather.current.temp_c}°",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 100.sp
                )

                Spacer(modifier = Modifier.padding(top = 80.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        modifier = Modifier.padding(start = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "humidity",
                            color = Color.LightGray,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "${weather.current.humidity}",
                            color = Color.White,
                            fontSize = 30.sp
                        )

                    }
                    Column(
                        modifier = Modifier.padding(start = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "cloud",
                            color = Color.LightGray,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "${weather.current.cloud}",
                            color = Color.White,
                            fontSize = 30.sp
                        )

                    }
                    Column(
                        modifier = Modifier.padding(start = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "feelslike_c",
                            color = Color.LightGray,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "${weather.current.feelslike_c}",
                            color = Color.White,
                            fontSize = 30.sp
                        )

                    }
                }
                Spacer(modifier = Modifier.padding(top = 20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        modifier = Modifier.padding(start = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "wind direction",
                            color = Color.LightGray,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "${weather.current.wind_dir}",
                            color = Color.White,
                            fontSize = 30.sp
                        )

                    }
                    Column(
                        modifier = Modifier.padding(start = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "wind_mph",
                            color = Color.LightGray,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "${weather.current.wind_mph}",
                            color = Color.White,
                            fontSize = 30.sp
                        )

                    }
                    Column(
                        modifier = Modifier.padding(start = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "wind_degree",
                            color = Color.LightGray,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "${weather.current.wind_degree}",
                            color = Color.White,
                            fontSize = 30.sp
                        )

                    }
                }

            }
        }


    }


}


@Preview(showBackground = true)
@Composable
fun WeatherLocationPreview() {

}