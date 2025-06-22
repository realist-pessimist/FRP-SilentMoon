package com.example.silentmoon

import com.example.silentmoon.frp.exercises.categorytheory.chapter3.ConcatMonoid
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Test : FunSpec({
    val monoid = ConcatMonoid()

    /*test("Monoid concat identity test") {
        val str = "Hello"
        monoid.concat(str, monoid.identity) shouldBe
                monoid.concat(monoid.identity, str)
    }*/
    test("Monoid concat associativity test") {
        val a = "Knowledge"
        val b = "is"
        val c = "power"
        monoid.concat(a, monoid.identity) shouldBe a
        monoid.concat(monoid.identity, a) shouldBe a
        monoid.concat(monoid.concat(a, b), c) shouldBe
                monoid.concat(a, monoid.concat(b, c))
    }
})