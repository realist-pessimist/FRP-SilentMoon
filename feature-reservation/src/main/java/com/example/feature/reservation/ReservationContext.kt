package com.example.feature.reservation

import com.example.core.CoreContext

interface ReservationContext : CoreContext {
  val tableRepository: TableRepository
}