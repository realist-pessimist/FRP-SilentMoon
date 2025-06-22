package com.example.silentmoon.frp.exercises.categorytheory.chapter4

import kotlin.math.sqrt

class Optional<A>(private val value: A?) {
  fun isValid(): Boolean = value != null
  fun getValue(): A = value ?: throw NoSuchElementException("No value present")

  companion object {
    fun <A> of(value: A): Optional<A> = Optional(value)
    fun <A> empty(): Optional<A> = Optional(null)
  }
}

fun safeRoot(x: Double): Optional<Double> {
  return if (x >= 0) {
    Optional.of(sqrt(x))
  } else {
    Optional.empty()
  }
}

fun <A, B, C> ((A) -> Optional<B>).bind(m2: (B) -> Optional<C>): (A) -> Optional<C> {
  return { x ->
    val optionalB = this(x)
    if (optionalB.isValid()) {
      m2(optionalB.getValue())
    } else {
      Optional.empty()
    }
  }
}

fun safeReciprocal(x: Double): Optional<Double> {
  return if (x != 0.0) {
    Optional.of(1 / x)
  } else {
    Optional.empty()
  }
}

fun process(x: Double): Optional<Double> {
  return ::safeRoot.bind { value ->
    Optional.of(value * 3)
  }(x)
}

//fun safeRootReciprocal(x: Double): Optional<Double> {
//    return safeReciprocal(x).bind { reciprocal ->
//        safeRoot(reciprocal)
//    }
//}

fun sample4() {
  val input = 4.0
  val result = process(input)
  if (result.isValid()) {
    println("Result: ${result.getValue()}")
  } else {
    println("No valid result")
  }

  val negativeInput = -4.0
  val negativeResult = process(negativeInput)
  if (negativeResult.isValid()) {
    println("Result: ${negativeResult.getValue()}")
  } else {
    println("No valid result") // Output: No valid result
  }

  val validInput = 4.0
  val reciprocalResult = safeReciprocal(validInput)
  if (reciprocalResult.isValid()) {
    println("Обратное значение для $validInput: ${reciprocalResult.getValue()}") // Вывод: Обратное значение для 4.0: 0.25
  } else {
    println("Нет валидного результата для $validInput")
  }

  val zeroInput = 0.0
  val zeroResult = safeReciprocal(zeroInput)
  if (zeroResult.isValid()) {
    println("Обратное значение для $zeroInput: ${zeroResult.getValue()}")
  } else {
    println("Нет валидного результата для $zeroInput") // Вывод: Нет валидного результата для 0.0
  }
}

