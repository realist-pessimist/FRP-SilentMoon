package com.example.silentmoon.frp.exercises.categorytheory.di

import arrow.core.Either
import arrow.core.raise.either
import com.example.analytics.AnalyticsContext
import com.example.analytics.AnalyticsTracker
import com.example.analytics.Event
import com.example.feature.reservation.DatabaseClient
import com.example.feature.reservation.DatabaseContext
import com.example.feature.reservation.RealDatabaseClient
import com.example.feature.reservation.ReservationContext
import com.example.feature.reservation.ReservationError
import com.example.feature.reservation.TableRepository
import java.util.logging.Logger

fun buildReservationContext(
  logger: Logger = Logger.getLogger("Reservation"),
  tableRepository: TableRepository = NetworkTableRepository(),
  databaseClient: DatabaseClient = RealDatabaseClient(),
  tracker: AnalyticsTracker = RealAnalyticsTracker()
): ReservationContextImpl {
  return ReservationContextImpl(
    logger = logger,
    tableRepository = tableRepository,
    databaseClient = databaseClient,
    tracker = tracker,
  )
}

class ReservationContextImpl(
  override val logger: Logger,
  override val tableRepository: TableRepository,
  override val databaseClient: DatabaseClient,
  override val tracker: AnalyticsTracker
) : ReservationContext, AnalyticsContext, DatabaseContext

class NetworkTableRepository : TableRepository {
  override suspend fun <Ctx> Ctx.checkTableAvailability(
    date: String,
    guests: Int
  ): Either<ReservationError, Boolean> where Ctx: DatabaseContext = either {
    databaseClient.query("SELECT * FROM tables WHERE date = '$date'")
      .mapLeft { ReservationError.DatabaseError(it) }
      .bind()
      .let { true } // Упрощенная логика
  }

  override suspend fun <Ctx> Ctx.reserveTable(
    tableId: Int,
    customerName: String
  ): Either<ReservationError, Boolean> where Ctx: DatabaseContext = either {
    databaseClient.executeUpdate("INSERT INTO reservations VALUES (...)")
      .mapLeft { ReservationError.DatabaseError(it) }
      .bind()
  }
}

class RealAnalyticsTracker : AnalyticsTracker {
  override fun track(event: Event) {
    // Реалная отправка аналитики
  }
}