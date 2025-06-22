package com.example.silentmoon.presentation.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun HSpacer(value: Dp) {
  Spacer(Modifier.width(value))
}

@Composable
fun VSpacer(value: Dp) {
  Spacer(Modifier.height(value))
}