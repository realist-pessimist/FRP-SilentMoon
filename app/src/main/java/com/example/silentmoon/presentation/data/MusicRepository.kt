package com.example.silentmoon.presentation.data

import arrow.core.Either
import arrow.core.Option
import arrow.core.raise.either
import arrow.core.raise.ensure

interface MusicRepository {
  suspend fun getMusicList(): Either<MusicError, List<MusicItem>>
  suspend fun getMusicById(id: MusicId): Either<MusicError, MusicItem>
  suspend fun toggleFavorite(id: MusicId): Either<MusicError, Boolean>
  suspend fun search(query: String): Either<MusicError, List<MusicItem>>
}

sealed interface MusicError {
  data object NetworkError : MusicError
  data object NotFound : MusicError
}
class MockMusicRepository : MusicRepository {

  private val mockMusicDatabase = mutableMapOf(
    MusicId("1") to MusicItem(
      id = MusicId("1"),
      title = "Focus Attention",
      artist = "7 Dys of calm",
      imageUrl = "",
      audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
      duration = 372000,
    ),
    MusicId("2") to MusicItem(
      id = MusicId("2"),
      title = "Good Night",
      artist = "",
      imageUrl = "",
      audioUrl = "",
      duration = 300,
      isFavorite = false,
      isDownloaded = false,
    ),
    MusicId("3") to MusicItem(
      id = MusicId("3"),
      title = "Sweet Sleep",
      artist = "",
      imageUrl = "",
      audioUrl = "",
      duration = 300,
      isFavorite = false,
      isDownloaded = false,
    ),
  )

  override suspend fun getMusicById(id: MusicId): Either<MusicError, MusicItem> =
    Option.fromNullable(mockMusicDatabase[id])
      .toEither { MusicError.NotFound }

  override suspend fun toggleFavorite(id: MusicId): Either<MusicError, Boolean> = either {
    val current = mockMusicDatabase[id] ?: raise(MusicError.NotFound)
    val updated = current.copy(isFavorite = !current.isFavorite)
    mockMusicDatabase[id] = updated
    updated.isFavorite
  }

  override suspend fun getMusicList(): Either<MusicError, List<MusicItem>> =
    Option.fromNullable(mockMusicDatabase.values.toList())
      .toEither { MusicError.NotFound }

  override suspend fun search(query: String): Either<MusicError, List<MusicItem>> = either {
    ensure(query.length >= 2) { MusicError.NetworkError }

    mockMusicDatabase.values
      .filter { it.title.contains(query, ignoreCase = true) }
      .let { results ->
        ensure(results.isNotEmpty()) { MusicError.NotFound }
        results
      }
  }
}