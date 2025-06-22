package com.example.feature.reservation

import arrow.core.flatMap
import com.example.analytics.AnalyticsContext
import com.example.analytics.Event
import java.io.Serializable
import java.util.logging.Level

suspend fun <Ctx> Ctx.makeReservation(request: ReservationRequest) : Result<Serializable>
        where Ctx : ReservationContext, Ctx : AnalyticsContext {
  return run {
    logger.info("Start")
    tableRepository.checkTableAvailability(request.date, request.tableId)
      .flatMap { isAvailable ->
        if (!isAvailable) {
          logger.log(Level.WARNING, "Столик на ${request.date} недоступен")
          Result.failure(IllegalStateException("Table unavailable"))
        } else {
          Result.success(Unit)
        }
      }
      .flatMap {
        tableRepository.reserveTable(request.tableId, request.customerName)
          .onSuccess {
            tracker.track(
              event = Event(eventParams = mapOf("reserve_table_success" to request.tableId))
            )
            logger.log(Level.INFO, "Столик #${request.tableId} успешно забронирован!")
          }
          .onFailure {
            logger.log(Level.WARNING, "Столик #${request.tableId} успешно забронирован!")
          }
      }
      .recoverCatching { e ->
        logger.log(Level.WARNING, "Ошибка: ${e.message}")
        Result.failure<Throwable>(e)
      }
  }
}