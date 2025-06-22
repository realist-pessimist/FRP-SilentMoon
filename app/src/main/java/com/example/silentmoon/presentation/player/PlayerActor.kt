package com.example.silentmoon.presentation.player

import arrow.core.Either
import com.example.silentmoon.elm.Actor
import com.example.silentmoon.elm.Command
import com.example.silentmoon.elm.Event
import com.example.silentmoon.presentation.data.AudioPlayer
import com.example.silentmoon.presentation.data.MusicId
import com.example.silentmoon.presentation.data.MusicItem
import com.example.silentmoon.presentation.data.MusicRepository
import com.example.silentmoon.presentation.data.PlaybackState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class PlayerActor(
  private val musicRepository: MusicRepository,
  private val player: AudioPlayer
) : Actor {

  override suspend fun execute(command: Command): Event = when (command) {
    is PlayerCommand.LoadMusicData -> loadMusicData(command.musicId)
    is PlayerCommand.Play -> playMusic(command.item)
    is PlayerCommand.Pause -> pauseMusic()
    is PlayerCommand.SeekTo -> seekTo(command.offsetMs)
    is PlayerCommand.Rewind -> rewind(command.offsetMs)
    is PlayerCommand.ToggleFavorite -> toggleFavorite(command.musicId)
    is PlayerCommand.Download -> download()
    else -> throw IllegalArgumentException("Unknown command")
  }

  override suspend fun subscribe(): Flow<Event> {
    return merge(
      player.playbackState.map { state ->
        when (state) {
          is PlaybackState.Buffering -> PlayerEvent.Buffering
          is PlaybackState.Ready -> PlayerEvent.Ready
          is PlaybackState.Ended -> PlayerEvent.Ended
          is PlaybackState.Idle -> PlayerEvent.Idle
        }
      },
      player.currentPosition.map { position ->
        PlayerEvent.PositionChanged(position)
      }
    )
  }

  private suspend fun loadMusicData(musicId: MusicId): Event {
    return when (val result = musicRepository.getMusicById(musicId)) {
      is Either.Right -> {
        PlayerEvent.DataLoaded(result.value)
      }
      is Either.Left -> PlayerEvent.Error("Error")
    }
  }

  private fun playMusic(musicItem: MusicItem): Event {
    player.play(musicItem.audioUrl)
    return PlayerEvent.Playing
  }

  private fun pauseMusic(): Event {
    player.pause()
    return PlayerEvent.Paused
  }

  private fun seekTo(offsetMs: Long): Event {
    player.seekBy(offsetMs)
    return PlayerEvent.Seeked(offsetMs)
  }

  private fun rewind(offsetMs: Long): Event {
    player.rewind(offsetMs)
    return PlayerEvent.Seeked(offsetMs)
  }

  private suspend fun toggleFavorite(musicId: MusicId): Event {
    return when (val result = musicRepository.toggleFavorite(musicId)) {
      is Either.Right -> PlayerEvent.FavoriteToggled(result.value)
      is Either.Left -> PlayerEvent.Error("Error")
    }
  }

  private fun download(): Event {
    return PlayerEvent.Download
  }
}