package com.example.silentmoon.presentation.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.silentmoon.R
import com.example.silentmoon.elm.Store
import com.example.silentmoon.presentation.data.MusicId
import com.example.silentmoon.presentation.ui.component.HSpacer
import com.example.silentmoon.presentation.ui.component.VSpacer
import com.example.silentmoon.presentation.utils.showToast
import kotlinx.coroutines.launch

enum class MusicMode {
  SLEEP,
  BASIC
}

@Composable
fun PlayerScreen(
  modifier: Modifier = Modifier,
  mode: MusicMode = MusicMode.BASIC,
  store: Store<PlayerState>,
  musicId: MusicId,
) {
  val state by store.state.collectAsState()
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()

  val currentPositionSeconds = remember { derivedStateOf { state.progress / 1000 } }
  val durationSeconds = remember { derivedStateOf { (state.musicItem?.duration ?: 0L) / 1000 } }
  LaunchedEffect(Unit) {
    coroutineScope.launch {
      store.send(PlayerEvent.LoadData(musicId))
    }
  }

  LaunchedEffect(store) {
    store.effects.collect { effect ->
      when (effect) {
        is PlayerEffect.ShowDownloadStarted -> {}
        is PlayerEffect.ShowError -> { showToast(context, effect.message) }
      }
    }
  }

  Box(modifier = modifier) {
    Image(
      painter = painterResource(
        id = when (mode) {
          MusicMode.SLEEP -> R.drawable.ic_music_player_sleep_background
          MusicMode.BASIC -> R.drawable.ic_music_player_basic_background
        }
      ),
      contentScale = ContentScale.FillWidth,
      contentDescription = null
    )
    Column(
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Spacer(Modifier.weight(1f))
      state.musicItem?.title?.let {
        Text(
          text =  it,
          color = Color(0xFF3F414E),
          fontWeight = FontWeight.Bold,
          fontSize = 34.sp
        )
      }
      state.musicItem?.artist?.let {
        Text(
          text =  it,
          color = Color(0xFFA0A3B1),
          fontWeight = FontWeight.Medium,
          fontSize = 14.sp
        )
      }
      VSpacer(50.dp)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Image(
          painter = painterResource(R.drawable.ic_backward),
          contentDescription = "",
          modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource()  },
            indication = null,
          ) {
            store.send(PlayerEvent.Seek(-15_000))
          }
        )
        HSpacer(50.dp)
        Box(
          modifier = modifier
            .size(88.dp)
            .clip(CircleShape)
            .background(Color(0xFF3F414E))
            .clickable(
              onClick = { store.send(PlayerEvent.PlayPause) },
              role = Role.Button,
            ),
          contentAlignment = Alignment.Center
        ) {
          if (!state.isPlaying)
            Icon(
              imageVector = Icons.Default.PlayArrow,
              contentDescription = "Play",
              tint = Color(0xFFFBFBFB),
              modifier = Modifier.size(36.dp)
            )
          else
            Icon(
              painter = painterResource(R.drawable.ic_pause),
              contentDescription = "Pause",
              tint = Color(0xFFFBFBFB),
              modifier = Modifier.size(24.dp)
            )
        }
        HSpacer(50.dp)
        Image(
          modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource()  },
            indication = null,
          ) {
            store.send(PlayerEvent.Seek(15_000))
          },
          painter = painterResource(R.drawable.ic_forward),
          contentDescription = ""
        )
      }
      VSpacer(40.dp)

      var sliderPosition by remember { mutableFloatStateOf(0f) }
      var isSliding by remember { mutableStateOf(false) }

      Slider(
        value = if (isSliding) sliderPosition else currentPositionSeconds.value.toFloat(),
        onValueChange = { newPosition ->
          isSliding = true
          sliderPosition = newPosition
        },
        onValueChangeFinished = {
          isSliding = false
          store.send(PlayerEvent.Rewind((sliderPosition * 1000).toLong()))
        },
        valueRange = 0f..durationSeconds.value.toFloat(),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 24.dp),
        colors = SliderDefaults.colors(
          thumbColor = Color(0xFF3F414E),
          activeTrackColor = Color(0xFF3F414E),
          inactiveTrackColor = Color(0xFFA0A3B1)
        )
      )

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = formatTime(currentPositionSeconds.value),
          color = Color(0xFF3F414E),
          fontSize = 12.sp,
          modifier = Modifier.padding(start = 24.dp)
        )
        Text(
          text = formatTime(durationSeconds.value),
          color = Color(0xFF3F414E),
          fontSize = 12.sp,
          modifier = Modifier.padding(end = 24.dp)
        )
      }
      Spacer(Modifier.weight(1f))
    }
  }
}

private fun formatTime(seconds: Long): String {
  val minutes = seconds / 60
  val remainingSeconds = seconds % 60
  return String.format("%02d:%02d", minutes, remainingSeconds)
}