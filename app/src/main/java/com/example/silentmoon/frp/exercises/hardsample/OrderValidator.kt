package com.example.silentmoon.frp.exercises.hardsample

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

object OrderValidator {
  suspend fun validate(order: Order): Either<OrderError, Order> = either {
    delay(30.milliseconds) // Имитация сетевого запроса
    ensure(order.items.isNotEmpty()) {
      OrderError.ValidationError("items", "Order cannot be empty")
    }
    ensure(!order.items.any { (_, qty) -> qty <= 0 }) {
      OrderError.ValidationError(
        "quantity",
        "Quantities must be positive"
      )
    }
    order
  }
}