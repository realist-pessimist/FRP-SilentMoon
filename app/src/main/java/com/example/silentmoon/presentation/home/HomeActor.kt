package com.example.silentmoon.presentation.home

import arrow.core.Either
import com.example.silentmoon.elm.Actor
import com.example.silentmoon.elm.Command
import com.example.silentmoon.elm.Event
import com.example.silentmoon.presentation.data.RecommendationRepository
import com.example.silentmoon.presentation.home.compose.Recommendation.Companion.createMockCourse
import com.example.silentmoon.presentation.home.compose.Recommendation.Companion.createMockMeditation
import com.example.silentmoon.presentation.home.compose.Recommendation.Companion.createMockMusic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.util.UUID

class HomeActor(
  private val recommendationRepository: RecommendationRepository,
) : Actor {
  override suspend fun execute(command: Command): Event = when (command) {
    is HomeCommand.LoadRecommendations -> loadHomeRecommendations()
    else -> throw IllegalArgumentException("Unknown command")
  }

  override suspend fun subscribe(): Flow<Event> {
    return emptyFlow()
  }

  private suspend fun loadHomeRecommendations(): Event {
    return when (val result = recommendationRepository.getRecommendations()) {
      is Either.Right -> HomeEvent.DataLoaded(
        Suggests(
          recommendations = result.value,
          course = createMockCourse(UUID.randomUUID().toString()),
          music = createMockMusic(UUID.randomUUID().toString()),
          meditation = createMockMeditation(UUID.randomUUID().toString())
        )
      )
      is Either.Left -> HomeEvent.Error(message = "Failed to load Recommendations: ${result.value}")
    }
  }
}