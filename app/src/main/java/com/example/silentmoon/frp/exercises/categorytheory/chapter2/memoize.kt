package com.example.silentmoon.frp.exercises.categorytheory.chapter2

//import arrow.core.memoize

//fun expensive(x: Int): Int {
//    Thread.sleep(x * 500L)
//    return x
//}
//
//fun generateRandomWithRange(range: IntRange): Int {
//    return expensive(range.random())
//}
//
//fun generateRandomWithSeed(seed: Int): Int {
//    val random = Random(seed)
//    return expensive(random.nextInt(1, 10))
//}
//
//val memoizedRandomWithSeedExpensive = ::generateRandomWithSeed.memoize()
//val memoizedRandomWithRangeExpensive = ::generateRandomWithRange.memoize()
//val memoizedExpensive = ::expensive.memoize()
//
//// Класс для управления состоянием генератора случайных чисел
//data class State<S, A>(val run: (S) -> Pair<A, S>)
//
//// Генерация случайного числа в рамках состояния
//fun randomNumber(): State<Random, Int> {
//    return State { gen ->
//        val number = gen.nextInt(1, 101) // Генерация числа от 1 до 100
//        number to gen // Возвращаем сгенерированное число и новый генератор
//    }
//}
//
//// Функция для выполнения состояния
//fun <S, A> runState(state: State<S, A>, initialState: S): Pair<A, S> {
//    return state.run(initialState)
//}
//
///***
// * Запоминающие функции работают только с чистыми функциями, которые не имеют побочных эффектов и всегда возвращают один и тот же результат для одинаковых входных данных. Нечистые функции, как правило, не подходят для запоминания, так как их поведение зависит от состояния, которое может изменяться.
// */
//fun sample2() {
//    val initialGen = Random((0..10).random()) // Начальное состояние генератора
//    val (num, _) = runState(randomNumber(), initialGen)
//    println(num)
//}