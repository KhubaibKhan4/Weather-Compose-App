package com.codespacepro.weathercomposeapp.navigation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.codespacepro.weathercomposeapp.R

@Composable
fun AboutScreen() {
    val typography = MaterialTheme.typography
    val uiHandler = LocalUriHandler.current
    val uri = "https://github.com/khubaibkhan4"

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Logo
            Image(
                painter = painterResource(R.drawable.world_map), // Replace with your app logo
                contentDescription = "App Logo",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // About Us Section
            Text(
                text = "About Us",
                style = typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Your weather app provides accurate and real-time weather information for users worldwide. Stay updated with the latest weather forecasts and conditions in your area and beyond.",
                style = typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Developer Information
            Text(
                text = "Developed by: Muhammad Khubaib Imtiaz",
                style = typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Privacy Policy Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.LightGray)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Privacy Policy",
                        style = typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Your privacy policy information goes here.",
                        style = typography.bodyMedium
                    )
                    IconButton(onClick = {
                        uiHandler.openUri(uri)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.open_in_new),
                            contentDescription = "",
                            tint = Color.Blue
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Terms and Conditions Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.LightGray)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Terms and Conditions",
                        style = typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Your terms and conditions information goes here.",
                        style = typography.bodyMedium
                    )
                    IconButton(onClick = {
                        uiHandler.openUri(uri)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.open_in_new),
                            contentDescription = "",
                            tint = Color.Blue
                        )
                    }
                }
            }
        }
    }
}
