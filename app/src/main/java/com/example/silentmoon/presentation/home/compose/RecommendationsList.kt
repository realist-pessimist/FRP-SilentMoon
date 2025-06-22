package com.example.silentmoon.presentation.home.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.silentmoon.presentation.ui.component.VSpacer

@Composable
fun RecommendationsList(
  recommendations: List<Recommendation>,
) {
  if (recommendations.isNotEmpty()) {
    VSpacer(20.dp)
    Text(
      text = "Recomended for you",
      color = Color(0xFF3F414E),
      fontWeight = FontWeight.Medium,
      fontSize = 24.sp
    )
    VSpacer(10.dp)
    LazyRow(
      modifier = Modifier,
      horizontalArrangement = Arrangement.spacedBy(19.dp)
    ) {
      items(recommendations) {
        Column(
          modifier = Modifier.width(162.dp)
        ) {
          Image(painter = painterResource(it.imageUrl), contentDescription = "")
          VSpacer(10.dp)
          Text(
            text = it.title,
            color = Color(0xFF3F414E),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
          )
          Text(
            text = "${it.typeText} ●  ${it.durationRange.first}-${it.durationRange.second} MIN",
            color = Color(0xFFA1A4B2),
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp
          )
        }
      }
    }
  }
}