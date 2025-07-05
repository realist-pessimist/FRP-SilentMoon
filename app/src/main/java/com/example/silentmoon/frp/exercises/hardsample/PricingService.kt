package com.example.silentmoon.frp.exercises.hardsample

import arrow.core.Either
import arrow.core.raise.either
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

object PricingService {
  private val discounts = mapOf(
    UserId("u_vip") to 0.15,
    UserId("u_regular") to 0.05
  )

  suspend fun calculateTotal(
    order: Order,
    userName: String,
    reservedItems: Map<ProductId, InventoryItem>
  ): Either<OrderError, Pair<Price, String>> = either {
    delay(80.milliseconds)
    val products = fetchProducts(order.items.keys.toList()).bind()
    val discount = discounts[order.userId] ?: 0.0

    val total = order.items.entries.fold(0.0) { acc, (productId, qty) ->
      val product = products.find { it.id == productId }
        ?: raise(OrderError.PricingError("Product ${productId.value} not found"))
      acc + (product.price.amount * qty)
    }

    val finalPrice = Price(total * (1 - discount), "USD")
    val receipt = buildReceipt(order, userName, products, finalPrice, reservedItems)

    finalPrice to receipt
  }

  private fun fetchProducts(ids: List<ProductId>): Either<OrderError, List<Product>> =
    either {
      ids.map { id ->
        when (id.value) {
          "p1" -> Product(id, "Laptop", Price(1200.0, "USD"))
          "p2" -> Product(id, "Phone", Price(800.0, "USD"))
          "p3" -> Product(id, "Tablet", Price(500.0, "USD"))
          else -> raise(OrderError.ProductNotFound(id))
        }
      }
    }

  private fun buildReceipt(
    order: Order,
    userName: String,
    products: List<Product>,
    price: Price,
    reservedItems: Map<ProductId, InventoryItem>
  ): String {
    val itemsDetails = order.items.entries.joinToString("\n") { (id, qty) ->
      val product = products.find { it.id == id }!!
      val reserved = reservedItems[id]!!
      "  - ${product.name} (${id.value}): $qty x ${product.price.amount} = ${qty * product.price.amount}" +
              " | Reserved: ${reserved.reserved}, Remaining: ${reserved.quantity}"
    }

    return """
            Order: ${order.id.value}
            User: $userName (${order.userId.value})
            Status: ${order.status}
            Items:
            $itemsDetails
            Total: ${price.amount} ${price.currency}
        """.trimIndent()
  }
}