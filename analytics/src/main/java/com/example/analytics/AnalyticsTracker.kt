package com.example.analytics

data class Event(val eventParams: Map<String, Any>)

interface AnalyticsTracker {
  fun track(event: Event)
}