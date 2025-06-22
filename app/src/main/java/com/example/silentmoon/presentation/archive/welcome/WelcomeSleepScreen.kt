package com.example.silentmoon.presentation.archive.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun WelcomeSleepScreen(
  modifier: Modifier = Modifier
) {
  Box(modifier = modifier) {
    Image(
      modifier = Modifier.fillMaxSize(),
      painter = painterResource(R.drawable.ic_welcome_sleep_background),
      contentDescription = null,
      contentScale = ContentScale.FillWidth
    )
    Column(
      modifier = Modifier
          .padding(top = 148.dp)
          .fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        modifier = Modifier.width(260.dp),
        text = "Welcome To Sleep",
        textAlign = TextAlign.Center,
        color = Color.White,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 30.sp,
        fontSize = 30.sp
      )
      Text(
        modifier = Modifier
            .padding(top = 15.dp)
            .width(317.dp),
        text = "Explore the new king of sleep. It uses sound and vesualization to create perfect conditions for refreshing sleep.",
        textAlign = TextAlign.Center,
        color = Color.White,
        fontWeight = FontWeight.Light,
        lineHeight = 24.sp,
        fontSize = 16.sp
      )
    }

    Button(
      modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(bottom = 64.dp)
          .fillMaxWidth()
          .height(64.dp)
          .padding(horizontal = 20.dp),
      shape = RoundedCornerShape(38.dp),
      colors = ButtonDefaults.buttonColors(
        containerColor = Color(0xFF8E97FD),
        contentColor = Color(0xFFF6F1FB)
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

@Preview
@Composable
fun WelcomeSleepScreen_Preview() {
  SilentMoonTheme {
    WelcomeSleepScreen()
  }
}