package com.example.silentmoon.frp.exercises.hardsample

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.update

object InventoryService {
  private val inventoryState = atomic(
    mapOf(
      ProductId("p1") to InventoryItem(ProductId("p1"), 10),
      ProductId("p2") to InventoryItem(ProductId("p2"), 5),
      ProductId("p3") to InventoryItem(ProductId("p3"), 0)
    )
  )

  private val mutex = Mutex()

  suspend fun checkAndReserve(item: Pair<ProductId, Int>): Either<OrderError, InventoryItem> = either {
    delay(50.milliseconds)
    val (productId, requested) = item

    mutex.withLock {
      val currentItems = inventoryState.value
      val currentItem = currentItems[productId] ?: raise(OrderError.ProductNotFound(productId))

      ensure(currentItem.quantity >= requested) {
        OrderError.InsufficientStock(productId, currentItem.quantity)
      }

      val updatedItem = currentItem.copy(
        quantity = currentItem.quantity - requested,
        reserved = currentItem.reserved + requested
      )

      inventoryState.update { it + (productId to updatedItem) }
      updatedItem
    }
  }

  fun getInventoryStatus(): Map<ProductId, InventoryItem> = inventoryState.value
}