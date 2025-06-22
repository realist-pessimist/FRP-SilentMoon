package com.example.silentmoon.presentation.data

import arrow.core.Either
import arrow.core.Option
import com.example.silentmoon.R
import com.example.silentmoon.presentation.home.compose.Recommendation
import com.example.silentmoon.presentation.home.compose.RecommendationId

interface RecommendationRepository {
  suspend fun getRecommendations(): Either<RecommendationsError, List<Recommendation>>
}

sealed interface RecommendationsError {
  data object NotFound : RecommendationsError
}

class MockRecommendationRepository : RecommendationRepository {
  private val mockRecommendationDatabase = mutableMapOf(
    RecommendationId("1") to
            Recommendation.createMockMeditation(
              id = "1",
              imageUrl = R.drawable.ic_recommendation_meditation1
            ),
    RecommendationId("2") to
            Recommendation.createMockMeditation(
              id = "2",
              imageUrl = R.drawable.ic_recommendation_meditation2
            ),
    RecommendationId("3") to
            Recommendation.createMockMeditation(
              id = "3",
              imageUrl = R.drawable.ic_recommendation_meditation1
            ),
  )

  override suspend fun getRecommendations(): Either<RecommendationsError, List<Recommendation>> =
    Option.fromNullable(mockRecommendationDatabase.values.take(3))
      .toEither { RecommendationsError.NotFound }
}