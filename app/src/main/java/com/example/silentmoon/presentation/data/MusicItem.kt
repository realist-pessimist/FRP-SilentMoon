package com.example.silentmoon.presentation.data

data class MusicId(val value: String)

data class MusicItem(
  val id: MusicId,
  val title: String,
  val artist: String,
  val duration: Long,
  val audioUrl: String,
  val imageUrl: String,
  val isFavorite: Boolean = false,
  val isDownloaded: Boolean = false
)

