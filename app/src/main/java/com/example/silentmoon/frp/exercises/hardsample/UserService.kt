package com.example.silentmoon.frp.exercises.hardsample

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

object UserService {
  private val users = mapOf(
    UserId("u_vip") to "VIP User",
    UserId("u_regular") to "Regular User",
    UserId("u_blocked") to "Blocked User"
  )

  private val blockedUsers = setOf(UserId("u_blocked"))

  suspend fun validateUser(userId: UserId): Either<OrderError, Pair<UserId, String>> = either {
    delay(100.milliseconds)
    ensure(!blockedUsers.contains(userId)) {
      OrderError.UserNotEligible(userId, "User is blocked")
    }
    val userName = users[userId] ?: raise(OrderError.UserNotEligible(userId, "User not found"))
    userId to userName
  }
}