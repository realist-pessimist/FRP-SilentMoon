package com.example.silentmoon.frp.exercises.categorytheory.di

import arrow.core.flatMap
import java.io.Serializable

interface TableRepository {
  suspend fun checkTableAvailability(date: String, guests: Int): Result<Boolean>
  suspend fun reserveTable(tableId: Int, customerName: String): Result<Boolean>
}

interface Notifier {
  fun showSuccess(message: String)
  fun showError(message: String)
}

interface AnalyticsTracker {
  fun trackReservationAttempt()
  fun trackReservationSuccess()
}


data class ReservationRequest(
  val tableId: Int,
  val customerName: String,
  val date: String,
  val guests: Int
)

// Реализации для Android
class NetworkTableRepository : TableRepository {
  override suspend fun checkTableAvailability(date: String, guests: Int): Result<Boolean> {
    // Реальная реализация с Retrofit/HttpURLConnection
    return Result.success(true) // Упрощенно для примера
  }

  override suspend fun reserveTable(tableId: Int, customerName: String): Result<Boolean> {
    // Реальная логика бронирования
    if (tableId % 2 == 0) {
      return Result.success(true) // Просто пример: четные ID = успех
    } else {
      return Result.failure(IllegalStateException("Ошибка бронирования"))
    }
  }
}

class FirebaseAnalyticsTracker : AnalyticsTracker {
  override fun trackReservationAttempt() {
    // Реальная реализация через Firebase
  }

  override fun trackReservationSuccess() {
    // Аналогично
  }
}

suspend fun <Ctx> Ctx.makeReservation(request: ReservationRequest): Result<Serializable>
        where Ctx : TableRepository, Ctx : Notifier, Ctx : AnalyticsTracker {
  return run {
    trackReservationAttempt()
    checkTableAvailability(request.date, request.guests)
      .flatMap { isAvailable ->
        if (!isAvailable) {
          showError("Столик на ${request.date} недоступен")
          Result.failure(IllegalStateException("Table unavailable"))
        } else {
          Result.success(Unit)
        }
      }
      .flatMap {
        reserveTable(request.tableId, request.customerName)
          .onSuccess {
            trackReservationSuccess()
            showSuccess("Столик #${request.tableId} успешно забронирован!")
          }
          .onFailure {
            showError("Ошибка бронирования")
          }
      }
      .recoverCatching { e ->
        showError("Ошибка: ${e.message}")
        Result.failure<Throwable>(e)
      }
  }
}