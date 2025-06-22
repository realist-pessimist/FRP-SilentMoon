package com.example.silentmoon.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.silentmoon.R

@Composable
fun Header(modifier: Modifier = Modifier) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(top = 36.dp),
    horizontalArrangement = Arrangement.Center
  ) {
    Text(
      text = "Silent",
      color = Color(0xFF3F414E),
      fontWeight = FontWeight.Bold,
      fontSize = 16.sp
    )
    HSpacer(10.dp)
    Icon(
      modifier = Modifier.size(30.dp),
      painter = painterResource(R.drawable.ic_logo),
      tint = Color.Unspecified,
      contentDescription = null
    )
    HSpacer(10.dp)
    Text(
      text = "Moon",
      color = Color(0xFF3F414E),
      fontWeight = FontWeight.Bold,
      fontSize = 16.sp
    )
  }
}