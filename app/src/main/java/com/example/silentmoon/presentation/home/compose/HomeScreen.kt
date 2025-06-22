package com.example.silentmoon.presentation.home.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.silentmoon.elm.Store
import com.example.silentmoon.presentation.home.HomeEffect
import com.example.silentmoon.presentation.home.HomeEvent
import com.example.silentmoon.presentation.home.HomeState
import com.example.silentmoon.presentation.ui.component.Header
import com.example.silentmoon.presentation.ui.component.VSpacer
import com.example.silentmoon.presentation.utils.showToast
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun HomeScreen(
  store: Store<HomeState>,
  onNavigateToMusic: (String) -> Unit,
  onNavigateToCourse: (String) -> Unit
) {
  val state by store.state.collectAsState()
  val context = LocalContext.current

  val coroutineScope = rememberCoroutineScope()
  LaunchedEffect(Unit) {
    coroutineScope.launch {
      store.send(HomeEvent.LoadData)
    }
  }

  LaunchedEffect(store) {
    store.effects.collect { effect ->
      when (effect) {
        is HomeEffect.NavigateToCourseScreen -> onNavigateToCourse(effect.id)
        is HomeEffect.NavigateToMusicScreen -> onNavigateToMusic(effect.id)
        is HomeEffect.ShowError -> { showToast(context, effect.message)
        }
      }
    }
  }

  if (state.isLoading) {
    CircularProgressIndicator()
  } else {
    HomeContent(
      recommendations = state.recommendations,
      dailyThought = state.dailyThought,
      suggestMusic = state.suggestMusic,
      suggestCourse = state.suggestCourse,
      onMusicSelected = {
        state.dailyThought?.id?.let {
          store.send(HomeEvent.NavigateToMusic(it.value))
        }
      },
      onCourseSelected = {
        state.suggestCourse?.id?.let {
          store.send(HomeEvent.NavigateToCourse(it.value))
        }
      }
    )
  }
}

@Composable
fun HomeContent(
  modifier: Modifier = Modifier,
  recommendations: List<Recommendation>,
  dailyThought: Recommendation.Meditation?,
  suggestMusic: Recommendation.Music?,
  suggestCourse: Recommendation.Course?,
  onMusicSelected: (String) -> Unit,
  onCourseSelected: (String) -> Unit,
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(horizontal = 16.dp)
  ) {
    Header()
    VSpacer(40.dp)
    Text(
      text = getGreetingText(),
      color = Color(0xFF3F414E),
      fontWeight = FontWeight.Bold,
      fontSize = 28.sp
    )
    VSpacer(10.dp)
    Text(
      text = "We Wish you have a good day",
      color = Color(0xFFA1A4B2),
      fontWeight = FontWeight.Light,
      fontSize = 20.sp
    )
    VSpacer(30.dp)
    Row(
      horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
      suggestCourse?.let {
        RecommendationCell(content = suggestCourse, onClick = onCourseSelected)
      }
      suggestMusic?.let {
        RecommendationCell(content = suggestMusic, onClick = onMusicSelected)
      }
    }
    VSpacer(20.dp)
    dailyThought?.let {
      DailyThoughtCell(content = dailyThought, onMusicSelected = onMusicSelected)
    }
    RecommendationsList(recommendations = recommendations)
  }
}

fun getGreetingText(): String {
  val calendar = Calendar.getInstance()
  val hour = calendar.get(Calendar.HOUR_OF_DAY)

  return when (hour) {
    in 5..11 -> "Good Morning!"
    in 12..17 -> "Good Day!"
    in 18..21 -> "Good Evening!"
    else -> "Good Night!"
  }
}