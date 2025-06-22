package com.example.silentmoon

import com.example.feature.reservation.ReservationRequest
import com.example.feature.reservation.TableRepository
import com.example.feature.reservation.makeReservation
import com.example.silentmoon.frp.exercises.categorytheory.di.buildReservationContext
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class FakeTableRepository: TableRepository {
  override suspend fun checkTableAvailability(date: String, guests: Int): Result<Boolean> {
    return Result.success(true)
  }
  override suspend fun reserveTable(tableId: Int, customerName: String): Result<Boolean> {
    return Result.success(true)
  }
}

class ReservationTest: FunSpec() {
  init {
    val fakeTableRepository = object : TableRepository by FakeTableRepository() {}
    test("Test reservation").config(coroutineTestScope = true) {
      val fakeContext = buildReservationContext(tableRepository = fakeTableRepository)
      val testRequest = ReservationRequest(
        tableId = 1,
        customerName = "Test",
        date = "2023-10-10",
        guests = 2
      )
      val result = fakeContext.makeReservation(testRequest)
      result.isSuccess shouldBe true
    }
  }
}
