package com.example.silentmoon.elm

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ElmStore<S : State>(
  initialState: S,
  private val actor: Actor,
  private val coroutineScope: CoroutineScope,
) : Store<S> {
  private val _state = MutableStateFlow(initialState)
  private val _effects = MutableSharedFlow<Effect>(extraBufferCapacity = 4)
  override val state = _state.asStateFlow()
  override val effects: SharedFlow<Effect> = _effects.asSharedFlow()

  init {
    coroutineScope.launch {
      actor.subscribe().collect { event ->
        processEvent(event)
      }
    }
  }

  override fun send(event: Event) {
    coroutineScope.launch {
      processEvent(event)
    }
  }

  private fun processEvent(event: Event) {
    val currentState = _state.value
    val result = currentState.reduce(event)

    @Suppress("UNCHECKED_CAST")
    _state.value = result.state as S

    result.commands.forEach { command ->
      coroutineScope.launch {
        val newEvent = actor.execute(command)
        processEvent(newEvent)
      }
    }

    // Обрабатываем эффекты (в UI)
    result.effects.forEach { effect ->
      coroutineScope.launch {
        _effects.emit(effect)
      }
    }
  }
}