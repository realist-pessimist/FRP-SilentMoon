package com.example.silentmoon.frp.exercises.hardsample

import arrow.core.Either
import kotlinx.coroutines.runBlocking

fun sampleHard() = runBlocking {
  // Example 1: Successful VIP order
  val order1 = Order(
    id = OrderId("order-1"),
    userId = UserId("u_vip"),
    items = mapOf(
      ProductId("p1") to 2,
      ProductId("p2") to 1
    )
  )

  processOrderExample(order1)

  // Example 2: Blocked user
  val order2 = Order(
    id = OrderId("order-2"),
    userId = UserId("u_blocked"),
    items = mapOf(ProductId("p1") to 1)
  )

  processOrderExample(order2)

  // Example 3: Insufficient stock
  val order3 = Order(
    id = OrderId("order-3"),
    userId = UserId("u_regular"),
    items = mapOf(ProductId("p3") to 1)
  )

  processOrderExample(order3)

  // Example 4: Invalid order
  val order4 = Order(
    id = OrderId("order-4"),
    userId = UserId("u_regular"),
    items = emptyMap()
  )

  processOrderExample(order4)

  // Example 5: Successful VIP order
  val order5 = Order(
    id = OrderId("order-5"),
    userId = UserId("u_vip"),
    items = mapOf(
      ProductId("p1") to 2,
      ProductId("p2") to 1
    )
  )

  processOrderExample(order5)

  // Print final inventory status
  println("\nFinal Inventory Status:")
  InventoryService.getInventoryStatus().forEach { (id, item) ->
    println("${id.value}: ${item.quantity} available, ${item.reserved} reserved")
  }
}


suspend fun processOrderExample(order: Order) {
  println("\nProcessing order ${order.id.value}...")

  when (val result = OrderProcessor.process(order)) {
    is Either.Right -> {
      val (_, receipt) = result.value
      println("\n✅ Order processed successfully:")
      println(receipt)
    }
    is Either.Left -> {
      val rejectedOrder = OrderProcessor.rejectOrder(order, result.value.toString())
      println("\n❌ Order rejected:")
      println("Order ID: ${rejectedOrder.id.value}")
      println("Status: ${rejectedOrder.status}")
      println("Reason: ${rejectedOrder.rejectionReason}")
    }
  }
}