package com.example.feature.reservation

interface TableRepository {
  suspend fun checkTableAvailability(date: String, guests: Int): Result<Boolean>
  suspend fun reserveTable(tableId: Int, customerName: String): Result<Boolean>
}

data class ReservationRequest(
  val tableId: Int,
  val customerName: String,
  val date: String,
  val guests: Int
)