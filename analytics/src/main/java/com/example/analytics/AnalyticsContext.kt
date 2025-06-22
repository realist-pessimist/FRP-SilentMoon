package com.example.analytics

import com.example.core.CoreContext

interface AnalyticsContext : CoreContext {
  val tracker: AnalyticsTracker
}