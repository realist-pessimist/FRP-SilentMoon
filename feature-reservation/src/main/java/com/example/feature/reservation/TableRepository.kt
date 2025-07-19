package com.example.feature.reservation

import arrow.core.Either

interface TableRepository {
  suspend fun <Ctx> Ctx.checkTableAvailability(
    date: String, guests: Int
  ): Either<ReservationError, Boolean> where Ctx: DatabaseContext

  suspend fun <Ctx> Ctx.reserveTable(
    tableId: Int, customerName: String
  ): Either<ReservationError, Boolean> where Ctx: DatabaseContext
}
interface DatabaseContext {
  val databaseClient: DatabaseClient
}

interface DatabaseClient {
  suspend fun query(sql: String): Either<Throwable, QueryResult>
  suspend fun executeUpdate(sql: String): Either<Throwable, Boolean>
}

class RealDatabaseClient : DatabaseClient {
  override suspend fun query(sql: String): Either<Throwable, QueryResult> =
    Either.catch {
      // Реальная реализация
      QueryResult(emptyList())
    }

  override suspend fun executeUpdate(sql: String): Either<Throwable, Boolean> =
    Either.catch {
      // Реальная реализация
      true
    }
}

data class QueryResult(val result: List<String>)

data class ReservationRequest(
  val tableId: Int,
  val customerName: String,
  val date: String,
  val guests: Int
)