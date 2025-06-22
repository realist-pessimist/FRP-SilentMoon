package com.example.silentmoon.presentation.archive.onboarding

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
import androidx.compose.foundation.text.BasicText
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.silentmoon.R
import com.example.silentmoon.presentation.ui.theme.SilentMoonTheme


@Composable
fun OnboardingScreen(
  modifier: Modifier = Modifier,
  navController: NavHostController
) {
  Column(
    modifier = modifier
        .fillMaxSize()
        .background(Color.White),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Box(
      modifier = Modifier.fillMaxWidth()
    ) {
      Image(
        painter = painterResource(R.drawable.ic_onboarding),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
      )
      Row(
        modifier = Modifier
            .padding(top = 36.dp)
            .align(Alignment.TopCenter),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Silent",
          color = Color(0xFF3F414E),
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp
        )
        Icon(
          modifier = Modifier.size(30.dp),
          painter = painterResource(R.drawable.ic_logo),
          tint = Color.Unspecified,
          contentDescription = null
        )
        Text(
          text = "Moon",
          color = Color(0xFF3F414E),
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp
        )
      }
    }
    Text(
      modifier = Modifier.padding(top = 30.dp),
      text = "We are what we do",
      textAlign = TextAlign.Center,
      color = Color(0xFF3F414E),
      fontWeight = FontWeight.SemiBold,
      lineHeight = 30.sp,
      fontSize = 30.sp
    )
    Text(
      modifier = Modifier
          .width(317.dp)
          .padding(top = 16.dp),
      text = "Thousand of people are usign silent moon for smalls meditation",
      textAlign = TextAlign.Center,
      color = Color(0xFFA1A4B2),
      fontWeight = FontWeight.Light,
      fontSize = 16.sp
    )

    Button(
      modifier = Modifier
          .padding(top = 60.dp)
          .fillMaxWidth()
          .height(64.dp)
          .padding(horizontal = 20.dp),
      shape = RoundedCornerShape(38.dp),
      colors = ButtonDefaults.buttonColors(
        containerColor = Color(0xFF8E97FD),
        contentColor = Color(0xFFF6F1FB)
      ),
      onClick = {
        navController.navigate("welcome")
      }
    ) {
      Text(
        text = "Sign up".uppercase(),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
      )
    }

    BasicText(
      modifier = Modifier.padding(top = 20.dp),
      text = AnnotatedString(
        text = "ALREADY HAVE AN ACCOUNT? LOG IN"
      ),
      style = TextStyle(
        color = Color(0xFFA1A4B2),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
      ),
    )
  }
}

@Preview
@Composable
fun Onboarding_Preview() {
  SilentMoonTheme {
    OnboardingScreen(navController = rememberNavController())
  }
}