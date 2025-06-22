package com.example.silentmoon.frp.exercises.categorytheory.chapter9

import arrow.core.curried
import arrow.core.partially1
import arrow.core.partially3
import arrow.core.partially4
import arrow.core.partially5

fun cookDish(
  prepTime: Int,
  cookTime: Int,
  ingredients: List<String>,
  equipment: List<String>,
  chefName: String
): String {
  val totalTime = prepTime + cookTime
  val ingredientsStr = ingredients.joinToString(", ")
  val equipmentStr = equipment.joinToString(", ")
  return "$chefName готовит блюдо из $ingredientsStr используя $equipmentStr. Общее время: $totalTime минут"
}

class DishCooker(
  private val chefName: String,
  private val defaultIngredients: List<String>,
  private val defaultEquipment: List<String>
) {
  fun cookDish(
    prepTime: Int,
    cookTime: Int,
    ingredients: List<String> = defaultIngredients,
    equipment: List<String> = defaultEquipment
  ): String {
    val totalTime = prepTime + cookTime
    val ingredientsStr = ingredients.joinToString(", ")
    val equipmentStr = equipment.joinToString(", ")
    return "$chefName готовит блюдо из $ingredientsStr используя $equipmentStr. Общее время: $totalTime минут"
  }
}

fun sample9_2() {
  val cookForChef = ::cookDish.partially5("Константин")
  val cookWithEquipment = cookForChef.partially4(listOf("сковорода", "нож"))
  val cookWithIngredients = cookWithEquipment.partially3(listOf("лук", "чеснок", "помидоры"))
  val cookQuickDish = cookWithIngredients.partially1(15)
  val cookSlowDish = cookWithIngredients.partially1(60)
  println(cookQuickDish(30))
  println(cookSlowDish(120))
  //Константин готовит блюдо из лук, чеснок, помидоры используя сковорода, нож. Общее время: 45 минут
  //Константин готовит блюдо из лук, чеснок, помидоры используя сковорода, нож. Общее время: 180 минут
}

fun sample9_3() {
  val antonCooker = DishCooker(
    chefName = "Антон",
    defaultIngredients = listOf("лук", "чеснок", "помидоры"),
    defaultEquipment = listOf("сковорода", "нож")
  )

  // Варианты приготовления:
  println(antonCooker.cookDish(prepTime = 15, cookTime = 30)) // быстрый вариант
  println(antonCooker.cookDish(prepTime = 60, cookTime = 120)) // медленный вариант

  // Можно переопределить ингредиенты для конкретного случая
  println(
    antonCooker.cookDish(
      prepTime = 20,
      cookTime = 40,
      ingredients = listOf("картофель", "грибы")
    )
  )
}

fun sample9_4() {
  val chefs = listOf("Антон", "Мария", "Иван")
  val ingredientsPresets = mapOf(
    "italian" to listOf("паста", "соус", "сыр"),
    "russian" to listOf("картофель", "грибы", "сметана")
  )
  val equipmentPresets = mapOf(
    "basic" to listOf("сковорода", "нож"),
    "pro" to listOf("мультиварка", "блендер", "точные весы")
  )
  val curriedDish = ::cookDish.curried()

  val dishes = chefs.flatMap { chef ->
    ingredientsPresets.flatMap { (_, ing) ->
      equipmentPresets.map { (_, eq) ->
        curriedDish(30)(60)(ing)(eq)(chef)
      }
    }
  }
  println(dishes.joinToString("\n"))
}