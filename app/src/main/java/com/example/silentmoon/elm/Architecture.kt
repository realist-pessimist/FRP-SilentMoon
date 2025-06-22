package com.example.silentmoon.elm

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface Event
interface Command
interface Effect
data class StateResult<S : State>(
  val state: S,
  val commands: List<Command> = emptyList(),
  val effects: List<Effect> = emptyList()
)

interface State {
  fun reduce(event: Event): StateResult<out State>
}

interface Actor {
  suspend fun execute(command: Command): Event
  suspend fun subscribe(): Flow<Event>
}

interface Store<S : State> {
  val state: StateFlow<S>
  val effects: SharedFlow<Effect>
  fun send(event: Event)
}