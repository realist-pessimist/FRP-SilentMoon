package com.example.silentmoon.frp.exercises.categorytheory.chapter3

class Vertex(val name: String)

class Morphism(val from: Vertex, val to: Vertex, val name: String)

class GraphA {
  val vertexA = Vertex("A")
  val idA = Morphism(vertexA, vertexA, "id_A")
}

class GraphB {
  val vertexB = Vertex("B")
  val idB = Morphism(vertexB, vertexB, "id_B")
  val f = Morphism(vertexB, vertexB, "f")
}

class GraphC {
  val vertexC = Vertex("C")
  val vertexD = Vertex("D")
  val idC = Morphism(vertexC, vertexC, "id_C")
  val idD = Morphism(vertexD, vertexD, "id_D")
  val g = Morphism(vertexC, vertexD, "g")
}

class GraphD {
  val vertexE = Vertex("E")
  val morphisms = ('a'..'z').map { letter ->
    Morphism(vertexE, vertexE, letter.toString())
  }
}

class ConcatMonoid {
  // Нейтральный элемент
  val identity: String = ""

  // Операция конкатенации
  fun concat(a: String, b: String): String {
    return a + b
  }
}

fun sample3() {
  // Пример использования графов
  val graphA = GraphA()
  val graphB = GraphB()
  val graphC = GraphC()
  val graphD = GraphD()

  println("Graph A: Vertex = ${graphA.vertexA.name}, Morphism = ${graphA.idA.name}")
  println("Graph B: Vertex = ${graphB.vertexB.name}, Morphism = ${graphB.idB.name}, ${graphB.f.name}")
  println("Graph C: Vertices = ${graphC.vertexC.name}, ${graphC.vertexD.name}, Morphisms = ${graphC.idC.name}, ${graphC.idD.name}, ${graphC.g.name}")
  println("Graph D: Vertex = ${graphD.vertexE.name}, Morphisms = ${graphD.morphisms.joinToString { it.name }}")
}