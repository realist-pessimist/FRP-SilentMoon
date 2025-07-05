package com.example.silentmoon.frp.exercises.hardsample

import arrow.core.Either
import arrow.core.raise.either
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun <A, B> List<A>.parTraverse(
  f: suspend (A) -> Either<OrderError, B>
): Either<OrderError, List<B>> = either {
  coroutineScope {
    map { a ->
      async { f(a) }
    }.awaitAll().let { results ->
      results.map { it.bind() }
    }
  }
}