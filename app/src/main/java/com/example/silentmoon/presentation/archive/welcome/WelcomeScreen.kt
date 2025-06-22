package com.example.silentmoon.presentation.archive.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.silentmoon.R
import com.example.silentmoon.presentation.ui.theme.SilentMoonTheme


@Composable
fun WelcomeScreen(
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
        .fillMaxSize()
        .background(Color(0xFF8E97FD)),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Row(
      modifier = Modifier.padding(top = 36.dp),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Silent",
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
      )
      Icon(
        modifier = Modifier.size(30.dp),
        painter = painterResource(R.drawable.ic_logo_night),
        tint = Color.Unspecified,
        contentDescription = null
      )
      Text(
        text = "Moon",
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
      )
    }
    Text(
      modifier = Modifier.padding(top = 75.dp),
      text = "Hi Eugene, Welcome\nto Silent Moon",
      textAlign = TextAlign.Center,
      color = Color.White,
      fontWeight = FontWeight.SemiBold,
      lineHeight = 30.sp,
      fontSize = 30.sp
    )
    Text(
      modifier = Modifier
          .width(317.dp)
          .padding(top = 16.dp),
      text = "Explore the app, Find some peace of mind to prepare for meditation.",
      textAlign = TextAlign.Center,
      color = Color.White,
      fontWeight = FontWeight.Light,
      fontSize = 16.sp
    )
    Box(
      modifier = Modifier
          .padding(top = 45.dp)
          .fillMaxWidth()
    ) {
      Image(
        painter = painterResource(R.drawable.ic_welcome),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
      )
      Button(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 48.dp)
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(38.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = Color(0xFFEBEAEC),
          contentColor = Color(0xFF3F414E)
        ),
        onClick = {}
      ) {
        Text(
          text = "Get Started".uppercase(),
          fontWeight = FontWeight.Medium,
          fontSize = 14.sp
        )
      }
    }
  }
}

@Preview
@Composable
fun WelcomeScreen_Preview() {
  SilentMoonTheme {
    WelcomeScreen()
  }
}