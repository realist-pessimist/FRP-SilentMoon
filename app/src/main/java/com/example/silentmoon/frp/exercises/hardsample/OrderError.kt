package com.example.silentmoon.frp.exercises.hardsample

sealed interface OrderError {
  data class ValidationError(val field: String, val message: String) : OrderError
  data class ProductNotFound(val productId: ProductId) : OrderError
  data class InsufficientStock(val productId: ProductId, val available: Int) : OrderError
  data class UserNotEligible(val userId: UserId, val reason: String) : OrderError
  data class PricingError(val message: String) : OrderError
}