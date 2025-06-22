package com.example.silentmoon.presentation.home.compose

import androidx.annotation.DrawableRes
import com.example.silentmoon.R

data class RecommendationId(val value: String)

sealed interface Recommendation {
  val id: RecommendationId
  val title: String
  val description: String
  val imageUrl: Int
  val durationRange: Pair<Long, Long>
  val typeText: String

  data class Meditation(
    override val id: RecommendationId,
    override val title: String,
    override val durationRange: Pair<Long, Long>,
    @DrawableRes override val imageUrl: Int,
    override val description: String,
    override val typeText: String = "MEDITATION"
  ) : Recommendation

  data class Music(
    override val id: RecommendationId,
    override val title: String,
    override val durationRange: Pair<Long, Long>,
    @DrawableRes override val imageUrl: Int,
    override val description: String,
    override val typeText: String = "MUSIC"
  ) : Recommendation

  data class Course(
    override val id: RecommendationId,
    override val title: String,
    override val durationRange: Pair<Long, Long>,
    @DrawableRes override val imageUrl: Int,
    override val description: String,
    override val typeText: String = "COURSE"
  ) : Recommendation


  companion object {
    fun createMockCourse(id: String) = Course(
      id = RecommendationId(id),
      title = "Basic",
      description = "",
      durationRange = 3L to 10L,
      imageUrl = R.drawable.ic_suggest_course
    )
    fun createMockMusic(id: String) = Music(
      id = RecommendationId(id),
      title = "Relaxation",
      description = "",
      durationRange = 3L to 10L,
      imageUrl = R.drawable.ic_suggest_music
    )
    fun createMockMeditation(
      id: String,
      @DrawableRes imageUrl: Int = R.drawable.ic_suggest_meditation,
    ) = Meditation(
      id = RecommendationId(id),
      title = "Relaxation",
      description = "",
      durationRange = 3L to 10L,
      imageUrl = imageUrl,
    )
  }
}