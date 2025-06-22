package com.example.silentmoon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.silentmoon.presentation.SilentMoonApp
import com.example.silentmoon.presentation.ui.theme.SilentMoonTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      SilentMoonTheme {
        SilentMoonApp()
      }
    }
  }
}