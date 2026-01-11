package com.example.ass6scaffold

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.sassawat_img),
            contentDescription = "Sassawat",
        )
        Text(text = "I am Rifle.")
        Text(text = "Sassawat Kempila")
        Text(text = "673380244-6")
    }
}