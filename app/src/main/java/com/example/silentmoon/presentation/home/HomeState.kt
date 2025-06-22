package com.example.silentmoon.presentation.home

import com.example.silentmoon.elm.Command
import com.example.silentmoon.elm.Effect
import com.example.silentmoon.elm.Event
import com.example.silentmoon.elm.State
import com.example.silentmoon.elm.StateResult
import com.example.silentmoon.presentation.home.compose.Recommendation

data class HomeState(
  val isLoading: Boolean = false,
  val suggestCourse: Recommendation.Course? = null,
  val suggestMusic: Recommendation.Music? = null,
  val dailyThought: Recommendation.Meditation? = null,
  val recommendations: List<Recommendation> = emptyList(),
  val error: String? = null
) : State {

  override fun reduce(event: Event): StateResult<out State> = when (event) {
    is HomeEvent.LoadData -> handleLoadData()
    is HomeEvent.DataLoaded -> handleDataLoaded(event)
    is HomeEvent.Error -> handleError(event)
    is HomeEvent.NavigateToMusic -> handleNavigateToMusic(event.id)
    is HomeEvent.NavigateToCourse -> handleNavigateToCourse(event.id)
    else -> StateResult(this)
  }

  private fun handleLoadData(): StateResult<HomeState> {
    return StateResult(
      state = copy(isLoading = true, error = null),
      commands = listOf(HomeCommand.LoadRecommendations)
    )
  }

  private fun handleDataLoaded(event: HomeEvent.DataLoaded): StateResult<HomeState> {
    return StateResult(
      state = copy(
        isLoading = false,
        recommendations = event.suggests.recommendations,
        suggestMusic = event.suggests.music,
        suggestCourse = event.suggests.course,
        dailyThought = event.suggests.meditation,
      )
    )
  }

  private fun handleError(event: HomeEvent.Error): StateResult<HomeState> {
    return StateResult(
      state = copy(isLoading = false, error = event.message),
      effects = listOf(HomeEffect.ShowError(event.message))
    )
  }

  private fun handleNavigateToMusic(id: String): StateResult<HomeState> {
    return StateResult(
      state = this,
      effects = listOf(HomeEffect.NavigateToMusicScreen(id))
    )
  }

  private fun handleNavigateToCourse(courseId: String): StateResult<HomeState> {
    return StateResult(
      state = this,
      effects = listOf(HomeEffect.NavigateToCourseScreen(courseId))
    )
  }
}

sealed class HomeEvent : Event {
  data object LoadData : HomeEvent()
  data class DataLoaded(
    val suggests: Suggests,
  ) : HomeEvent()
  data class Error(val message: String) : HomeEvent()
  data class NavigateToMusic(val id: String) : HomeEvent()
  data class NavigateToCourse(val id: String) : HomeEvent()
}

sealed class HomeCommand : Command {
  data object LoadRecommendations : HomeCommand()
}

sealed class HomeEffect : Effect {
  data class ShowError(val message: String) : HomeEffect()
  data class NavigateToMusicScreen(val id: String) : HomeEffect()
  data class NavigateToCourseScreen(val id: String) : HomeEffect()
}

data class Suggests(
  val recommendations: List<Recommendation>,
  val music: Recommendation.Music?,
  val course: Recommendation.Course?,
  val meditation: Recommendation.Meditation?,
)