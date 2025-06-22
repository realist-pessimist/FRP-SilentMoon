package com.example.silentmoon.presentation.data

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


interface AudioPlayer {
  fun play(url: String)
  fun pause()
  fun seekBy(offsetMs: Long)
  fun rewind(offsetMs: Long)
  fun release()

  val playbackState: Flow<PlaybackState>
  val currentPosition: Flow<Long>
}

class ExoAudioPlayer(
  context: Context,
  private val coroutineScope: CoroutineScope
) : AudioPlayer {
  private var exoPlayer: ExoPlayer? = null
  private val _playbackState = MutableStateFlow<PlaybackState>(PlaybackState.Idle)
  private val _currentPosition = MutableStateFlow(0L)

  init {
    initializePlayer(context)
  }

  private fun initializePlayer(context: Context) {
    exoPlayer = ExoPlayer.Builder(context)
      .setAudioAttributes(
        AudioAttributes.Builder()
          .setUsage(C.USAGE_MEDIA)
          .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
          .build(),
        true
      )
      .setHandleAudioBecomingNoisy(true)
      .build()
      .apply {
        addListener(object : Player.Listener {
          override fun onPlaybackStateChanged(state: Int) {
            _playbackState.value = when (state) {
              Player.STATE_READY -> PlaybackState.Ready
              Player.STATE_BUFFERING -> PlaybackState.Buffering
              Player.STATE_ENDED -> PlaybackState.Ended
              Player.STATE_IDLE -> PlaybackState.Idle
              else -> PlaybackState.Idle
            }
          }

          override fun onIsPlayingChanged(isPlaying: Boolean) {
            if (isPlaying) {
              startPositionUpdates()
            }
          }
        })
      }
  }

  override fun play(url: String) {
    exoPlayer?.let { player ->
      when {
        // Новый трек
        player.mediaItemCount == 0 ||
                player.currentMediaItem?.localConfiguration?.uri?.toString() != url -> {
          player.setMediaItem(MediaItem.fromUri(url))
          player.prepare()
        }

        // Трек закончился
        player.playbackState == Player.STATE_ENDED -> {
          player.seekTo(0)
        }

        // Продолжаем воспроизведение
        else -> {
          // Позиция сохраняется автоматически
        }
      }
      player.play()
    }
  }

  override fun pause() {
    exoPlayer?.pause()
  }

  override fun seekBy(offsetMs: Long) {
    exoPlayer?.let { player ->
      val duration = player.duration
      if (duration != C.TIME_UNSET) {
        val newPosition = player.currentPosition + offsetMs
        player.seekTo(newPosition.coerceIn(0, player.duration))
      }
    }
  }

  override fun rewind(offsetMs: Long) {
    exoPlayer?.let { player ->
      val duration = player.duration
      if (duration != C.TIME_UNSET) {
        player.seekTo(offsetMs)
      }
    }
  }

  override fun release() {
    stopPositionUpdates()
    exoPlayer?.release()
    exoPlayer = null
  }

  override val playbackState: Flow<PlaybackState> = _playbackState
  override val currentPosition: Flow<Long> = _currentPosition

  private fun startPositionUpdates() {
    coroutineScope.launch {
      while (exoPlayer?.isPlaying == true) {
        exoPlayer?.currentPosition?.let { pos ->
          _currentPosition.value = pos
        }
        delay(200)
      }
    }
  }

  private fun stopPositionUpdates() {
    _currentPosition.value = 0L
  }
}

sealed class PlaybackState {
  data object Idle : PlaybackState()
  data object Ready : PlaybackState()
  data object Buffering : PlaybackState()
  data object Ended : PlaybackState()
}