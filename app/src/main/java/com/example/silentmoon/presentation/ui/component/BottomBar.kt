package com.example.silentmoon.presentation.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import com.example.silentmoon.R
import com.example.silentmoon.presentation.ui.component.Destination.Companion.startDestination

enum class Destination(
  val route: String,
  val label: String,
  val contentDescription: String,
  @DrawableRes val icon: Int
) {
  HOME(
    route = "home",
    label = "Home",
    contentDescription = "Home screen",
    icon = R.drawable.ic_home
  ),
  SLEEP(
    route = "sleep",
    label = "Sleep",
    contentDescription = "Sleep screen",
    icon = R.drawable.ic_sleep
  ),
  MEDITATION(
    route = "meditation",
    label = "Meditation",
    contentDescription = "Meditation screen",
    icon = R.drawable.ic_meditate
  ),
  MUSIC(
    route = "music",
    label = "Music",
    contentDescription = "Music screen",
    icon = R.drawable.ic_music
  );

  companion object {
    val startDestination = HOME
  }
}

@Composable
fun BottomBar() {
  val navController = rememberNavController()
  var selectedDestination by rememberSaveable {
    mutableIntStateOf(startDestination.ordinal)
  }

  NavigationBar(
    windowInsets = NavigationBarDefaults.windowInsets,
    containerColor = Color.White,
  ) {
    Destination.entries.forEachIndexed { index, destination ->
      NavigationBarItem(
        selected = selectedDestination == index,
        onClick = {
          navController.navigate(route = destination.route)
          selectedDestination = index
        },
        icon = {
          Icon(
            painter = painterResource(destination.icon),
            contentDescription = destination.contentDescription,
          )
        },
        label = {
          Text(
            destination.label,
            color = Color.Gray
          )
        },
        colors = NavigationBarItemColors(
          selectedIconColor = Color.White,
          unselectedIconColor = Color.Gray,
          selectedTextColor = Color.White,
          unselectedTextColor = Color.Gray,
          selectedIndicatorColor = Color(0xFF8E97FD),
          disabledIconColor = Color.LightGray,
          disabledTextColor = Color.LightGray,
        )
      )
    }
  }
}