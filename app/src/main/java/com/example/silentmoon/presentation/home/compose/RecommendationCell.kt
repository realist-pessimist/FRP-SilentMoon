package com.example.silentmoon.presentation.home.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecommendationCell(
  modifier: Modifier = Modifier,
  content: Recommendation,
  onClick: (String) -> Unit,
) {
  Box(modifier = modifier.width(177.dp).height(210.dp)) {
    Image(painter = painterResource(content.imageUrl), contentDescription = "")
    Column(
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
      Spacer(Modifier.weight(1f))
      Text(
        text = content.title,
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
      )
      Text(
        text = content.typeText,
        color = Color.White,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
      )
      Spacer(Modifier.weight(1f))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "${content.durationRange.first}-${content.durationRange.second} MIN",
          color = Color.White,
          fontSize = 12.sp,
          fontWeight = FontWeight.Medium,
        )
        Button(
          onClick = { onClick(content.id.value) }
        ) {
          Text(
            text = "START",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
          )
        }
      }
    }
  }
}