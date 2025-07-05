package com.example.silentmoon.frp.exercises.hardsample

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.recover

object OrderProcessor {
  suspend fun process(order: Order): Either<OrderError, Pair<Order, String>> = either {
    val (_, userName) = UserService.validateUser(order.userId).bind()
    val validatedOrder = OrderValidator.validate(order).bind()

    val reservedItems = reserveInventory(validatedOrder.items).bind()

    val (_, receipt) = PricingService.calculateTotal(
      order = validatedOrder,
      userName = userName,
      reservedItems = reservedItems.associateBy { it.productId }
    ).bind()

    validatedOrder.copy(status = OrderStatus.CONFIRMED) to receipt
  }.recover { error ->
    raise(error)
  }

  fun rejectOrder(order: Order, reason: String): Order {
    return order.copy(
      status = OrderStatus.REJECTED,
      rejectionReason = reason
    )
  }

  private suspend fun reserveInventory(items: Map<ProductId, Int>): Either<OrderError, List<InventoryItem>> =
    items.toList().parTraverse { (id, qty) ->
      InventoryService.checkAndReserve(id to qty)
    }
}