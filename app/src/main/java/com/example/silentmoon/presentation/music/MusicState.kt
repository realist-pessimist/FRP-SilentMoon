package com.example.silentmoon.presentation.music

import com.example.silentmoon.elm.Command
import com.example.silentmoon.elm.Effect
import com.example.silentmoon.elm.Event
import com.example.silentmoon.elm.State
import com.example.silentmoon.elm.StateResult
import com.example.silentmoon.presentation.data.MusicItem

data class MusicState(
  val isLoading: Boolean = false,
  val musicList: List<MusicItem> = emptyList(),
  val error: String? = null
) : State {
  override fun reduce(event: Event): StateResult<out State> = when (event) {
    is MusicEvent.LoadData -> handleLoadData()
    is MusicEvent.DataLoaded -> handleDataLoaded(event)
    is MusicEvent.Error -> handleError(event)
    is MusicEvent.SelectMusic -> handleSelectMusic(event)
    else -> StateResult(this)
  }

  private fun handleLoadData(): StateResult<MusicState> {
    return StateResult(
      state = copy(isLoading = true, error = null),
      commands = listOf(MusicCommand.LoadMusicList)
    )
  }

  private fun handleDataLoaded(event: MusicEvent.DataLoaded): StateResult<MusicState> {
    return StateResult(
      state = copy(isLoading = false, musicList = event.musicList)
    )
  }

  private fun handleError(event: MusicEvent.Error): StateResult<MusicState> {
    return StateResult(
      state = copy(isLoading = false, error = event.message),
      effects = listOf(MusicEffect.ShowError(event.message))
    )
  }

  private fun handleSelectMusic(event: MusicEvent.SelectMusic): StateResult<MusicState> {
    return StateResult(
      state = this,
      effects = listOf(MusicEffect.NavigateToDetail(event.musicId))
    )
  }
}

sealed class MusicEvent : Event {
  data object LoadData : MusicEvent()
  data class DataLoaded(val musicList: List<MusicItem>) : MusicEvent()
  data class Error(val message: String) : MusicEvent()
  data class SelectMusic(val musicId: String) : MusicEvent()
}

sealed class MusicCommand : Command {
  data object LoadMusicList : MusicCommand()
}

sealed class MusicEffect : Effect {
  data class ShowError(val message: String) : MusicEffect()
  data class NavigateToDetail(val musicId: String) : MusicEffect()
}