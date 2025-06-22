package com.example.silentmoon.presentation.home.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DailyThoughtCell(
  modifier: Modifier = Modifier,
  content: Recommendation,
  onMusicSelected: (String) -> Unit,
) {
  Box(modifier = modifier.width(374.dp).height(95.dp)) {
    Image(painter = painterResource(content.imageUrl), contentDescription = "")
    Row(
      modifier = Modifier.fillMaxSize().padding(horizontal = 30.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Column {
        Text(
          text = "Daily Thought",
          color = Color.White,
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
        )
        Text(
          text = "${content.typeText} ● " +
                  "${content.durationRange.first}-" +
                  "${content.durationRange.second} MIN",
          color = Color.White,
          fontSize = 12.sp,
          fontWeight = FontWeight.Medium,
        )
      }
      Box(
        modifier = modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(Color.White)
          .clickable(
            onClick = { onMusicSelected(content.id.value) },
            role = Role.Button,
          ),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.PlayArrow,
          contentDescription = "Play",
          tint = Color(0xFF3F414E),
          modifier = Modifier.size(32.dp)
        )
      }
    }
  }
}