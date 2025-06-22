package com.example.silentmoon.frp.exercises.categorytheory.di

import com.example.analytics.AnalyticsContext
import com.example.analytics.AnalyticsTracker
import com.example.analytics.Event
import com.example.feature.reservation.ReservationContext
import com.example.feature.reservation.TableRepository
import java.util.logging.Logger

fun buildReservationContext(
  logger: Logger = Logger.getLogger("Reservation"),
  tableRepository: TableRepository = NetworkTableRepository(),
  tracker: AnalyticsTracker = RealAnalyticsTracker()
): ReservationContextImpl {
  return ReservationContextImpl(
    logger = logger,
    tableRepository = tableRepository,
    tracker = tracker
  )
}

class ReservationContextImpl(
  override val logger: Logger,
  override val tableRepository: TableRepository,
  override val tracker: AnalyticsTracker
) : ReservationContext, AnalyticsContext

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

class RealAnalyticsTracker : AnalyticsTracker {
  override fun track(event: Event) {
    // Реалная отправка аналитики
  }
}