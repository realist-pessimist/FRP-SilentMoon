package com.example.silentmoon.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.silentmoon.elm.ElmStore
import com.example.silentmoon.presentation.data.ExoAudioPlayer
import com.example.silentmoon.presentation.data.MockMusicRepository
import com.example.silentmoon.presentation.data.MockRecommendationRepository
import com.example.silentmoon.presentation.data.MusicId
import com.example.silentmoon.presentation.home.HomeActor
import com.example.silentmoon.presentation.home.HomeState
import com.example.silentmoon.presentation.home.compose.HomeScreen
import com.example.silentmoon.presentation.player.MusicMode
import com.example.silentmoon.presentation.player.PlayerActor
import com.example.silentmoon.presentation.player.PlayerScreen
import com.example.silentmoon.presentation.player.PlayerState
import com.example.silentmoon.presentation.ui.component.BottomBar
import com.example.silentmoon.presentation.ui.component.Destination
import com.example.silentmoon.presentation.ui.component.Destination.Companion.startDestination

@Composable
fun SilentMoonApp() {
  val navController = rememberNavController()
  val currentRoute by navController.currentBackStackEntryAsState()
  val shouldShowBottomBar = remember(currentRoute) {
    currentRoute?.destination?.route in setOf(
      Destination.HOME.route,
      Destination.SLEEP.route,
      Destination.MEDITATION.route,
      Destination.MUSIC.route
    )
  }
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()
  val recommendationRepository = remember { MockRecommendationRepository() }
  val musicRepository = remember { MockMusicRepository() }
  val audioPlayer = remember { ExoAudioPlayer(context = context, coroutineScope = coroutineScope) }
  val homeActor = remember { HomeActor(recommendationRepository) }
  val playerActor = remember { PlayerActor(musicRepository, audioPlayer) }

  val homeStore = remember {
    ElmStore(
      initialState = HomeState(),
      actor = homeActor,
      coroutineScope = coroutineScope,
    )
  }

  val playerStore = remember {
    ElmStore(
      initialState = PlayerState(),
      actor = playerActor,
      coroutineScope = coroutineScope,
    )
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    bottomBar = {
      AnimatedVisibility(
        visible = shouldShowBottomBar,
        exit = fadeOut(),
        enter = fadeIn()
      ) { BottomBar() }
    }
  ) { contentPadding ->
    NavHost(
      modifier = Modifier.padding(contentPadding),
      navController = navController,
      startDestination = startDestination.route,
    ) {
      composable(Destination.HOME.route) {
        HomeScreen(
          store = homeStore,
          onNavigateToMusic = { navController.navigate("music/$it") },
          onNavigateToCourse = { navController.navigate("course/$it") }
        )
      }
      composable(Destination.SLEEP.route) {  }
      composable(Destination.MEDITATION.route) {  }
      composable(Destination.MUSIC.route) {  }
      composable("music/{id}") { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")
        require(id != null)
        PlayerScreen(
          mode = MusicMode.BASIC,
          store = playerStore,
          musicId = MusicId("1"), // Need for mock request
        )
      }
    }
  }
}