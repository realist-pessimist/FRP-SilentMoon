package com.example.silentmoon.presentation.player

import com.example.silentmoon.elm.Command
import com.example.silentmoon.elm.Effect
import com.example.silentmoon.elm.Event
import com.example.silentmoon.elm.State
import com.example.silentmoon.elm.StateResult
import com.example.silentmoon.presentation.data.MusicId
import com.example.silentmoon.presentation.data.MusicItem

data class PlayerState(
  val musicItem: MusicItem? = null,
  val isPlaying: Boolean = false,
  val progress: Long = 0,
  val isFavorite: Boolean = false,
  val isDownloaded: Boolean = false,
  val error: String? = null
) : State {
  override fun reduce(event: Event): StateResult<out State> = when (event) {
    is PlayerEvent.LoadData -> handleLoadData(event)
    is PlayerEvent.DataLoaded -> handleDataLoaded(event)
    is PlayerEvent.Error -> handleError(event)
    is PlayerEvent.PlayPause -> handlePlayPause()
    is PlayerEvent.Seek -> handleSeek(event)
    is PlayerEvent.Rewind -> handleRewind(event)
    is PlayerEvent.ToggleFavorite -> handleToggleFavorite(event.musicId)
    is PlayerEvent.Download -> handleDownload()
    is PlayerEvent.PositionChanged -> handlePositionChanged(event.position)
    is PlayerEvent.Ended -> handleEndedTrack()
    else -> StateResult(this)
  }

  private fun handleLoadData(event: PlayerEvent.LoadData): StateResult<PlayerState> {
    return StateResult(
      state = copy(error = null),
      commands = listOf(PlayerCommand.LoadMusicData(event.musicId))
    )
  }

  private fun handleDataLoaded(event: PlayerEvent.DataLoaded): StateResult<PlayerState> {
    return StateResult(
      state = copy(musicItem = event.musicItem)
    )
  }

  private fun handleError(event: PlayerEvent.Error): StateResult<PlayerState> {
    return StateResult(
      state = copy(error = event.message),
      effects = listOf(PlayerEffect.ShowError(event.message))
    )
  }

  private fun handlePlayPause(): StateResult<PlayerState> {
    require(musicItem != null)
    return StateResult(
      state = copy(isPlaying = !isPlaying),
      commands = listOf(
        if (isPlaying) PlayerCommand.Pause
        else PlayerCommand.Play(musicItem)
      )
    )
  }

  private fun handleSeek(event: PlayerEvent.Seek): StateResult<PlayerState> {
    return StateResult(
      state = this,
      commands = listOf(PlayerCommand.SeekTo(event.offsetMs))
    )
  }

  private fun handleRewind(event: PlayerEvent.Rewind): StateResult<PlayerState> {
    return StateResult(
      state = this,
      commands = listOf(PlayerCommand.Rewind(event.offsetMs))
    )
  }

  private fun handleToggleFavorite(id: MusicId): StateResult<PlayerState> {
    return StateResult(
      state = copy(isFavorite = !isFavorite),
      commands = listOf(PlayerCommand.ToggleFavorite(id))
    )
  }

  private fun handleDownload(): StateResult<PlayerState> {
    return StateResult(
      state = copy(isDownloaded = true),
      commands = listOf(PlayerCommand.Download),
      effects = listOf(PlayerEffect.ShowDownloadStarted)
    )
  }

  private fun handlePositionChanged(position: Long): StateResult<PlayerState> {
    return StateResult(
      state = copy(progress = position),
    )
  }

  private fun handleEndedTrack(): StateResult<PlayerState> {
    return StateResult(
      state = copy(progress = 0L, isPlaying = false),
    )
  }
}

sealed class PlayerEvent : Event {
  data class LoadData(val musicId: MusicId) : PlayerEvent()
  data class DataLoaded(val musicItem: MusicItem) : PlayerEvent()
  data class Error(val message: String) : PlayerEvent()
  data object PlayPause : PlayerEvent()
  data class Seek(val offsetMs: Long) : PlayerEvent()
  data class Rewind(val offsetMs: Long) : PlayerEvent()
  data class ToggleFavorite(val musicId: MusicId) : PlayerEvent()
  data object Download : PlayerEvent()

  // New events for player state
  data object Playing : PlayerEvent()
  data object Paused : PlayerEvent()
  data object Buffering : PlayerEvent()
  data object Ready : PlayerEvent()
  data object Ended : PlayerEvent()
  data object Idle : PlayerEvent()
  data class PositionChanged(val position: Long) : PlayerEvent()
  data class Seeked(val offsetMs: Long) : PlayerEvent()
  data class FavoriteToggled(val isFavorite: Boolean) : PlayerEvent()
}

sealed class PlayerCommand : Command {
  data class LoadMusicData(val musicId: MusicId) : PlayerCommand()
  data class Play(val item: MusicItem) : PlayerCommand()
  data object Pause : PlayerCommand()
  data class SeekTo(val offsetMs: Long) : PlayerCommand()
  data class Rewind(val offsetMs: Long) : PlayerCommand()
  data class ToggleFavorite(val musicId: MusicId) : PlayerCommand()
  data object Download : PlayerCommand()
}

sealed class PlayerEffect : Effect {
  data class ShowError(val message: String) : PlayerEffect()
  data object ShowDownloadStarted : PlayerEffect()
}