package com.example.ass6scaffold

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource

@Composable
fun Friend2Screen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.bellpineapple),
            contentDescription = "Bell",
        )
        Text(text = "Bell")
        Text(text = "Yongsin Tarabut")
    }
}