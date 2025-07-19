package com.example.feature.reservation

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.example.analytics.AnalyticsContext
import com.example.analytics.Event

suspend fun <Ctx> Ctx.makeReservation(request: ReservationRequest): Either<ReservationError, ReservationSuccess>
        where Ctx : ReservationContext, Ctx : AnalyticsContext, Ctx : DatabaseContext = either {
  logger.info("Starting reservation for ${request.customerName}")

  val isAvailable = tableRepository
    .run { this@makeReservation.checkTableAvailability(request.date, request.guests) }
    .bind()

  ensure(isAvailable) {
    logger.warning("Table unavailable for ${request.date}")
    ReservationError.TableUnavailable(request.date)
  }

  // Бронирование
  tableRepository
    .run { this@makeReservation.reserveTable(request.tableId, request.customerName) }
    .bind()
    .also {
      tracker.track(Event(mapOf(
        "event" to "reservation_success",
        "table_id" to request.tableId
      )))
      logger.info("Table #${request.tableId} reserved!")
    }

  ReservationSuccess(request.tableId, request.date)
}

sealed interface ReservationError {
  data class TableUnavailable(val date: String) : ReservationError
  data class DatabaseError(val cause: Throwable) : ReservationError
}

data class ReservationSuccess(val tableId: Int, val date: String)