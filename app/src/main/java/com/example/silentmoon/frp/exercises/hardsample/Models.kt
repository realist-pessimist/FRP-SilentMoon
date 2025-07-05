package com.example.silentmoon.frp.exercises.hardsample

data class UserId(val value: String)
data class ProductId(val value: String)
data class OrderId(val value: String)
data class Price(val amount: Double, val currency: String)
data class Product(val id: ProductId, val name: String, val price: Price)
data class InventoryItem(val productId: ProductId, val quantity: Int, val reserved: Int = 0)
data class Order(
  val id: OrderId,
  val userId: UserId,
  val items: Map<ProductId, Int>,
  val status: OrderStatus = OrderStatus.PENDING,
  val rejectionReason: String? = null
)

enum class OrderStatus { PENDING, CONFIRMED, REJECTED }