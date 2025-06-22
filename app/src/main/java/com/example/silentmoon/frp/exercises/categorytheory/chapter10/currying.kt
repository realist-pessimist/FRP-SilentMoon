package com.example.silentmoon.frp.exercises.categorytheory.chapter10

import arrow.core.curried
import java.time.LocalDate
import java.util.Locale
import java.util.logging.Logger

enum class Priority { LOW, MEDIUM, HIGH }
data class Task(
  val title: String,
  val description: String,
  val priority: Priority,
  val dueDate: LocalDate,
  val tags: Set<String>,
  val estimatedHours: Double
)

fun createTask(
  locale: Locale,
  logger: Logger,
  validator: (Task) -> Boolean,
  title: String,
  description: String,
  priority: Priority,
  dueDate: LocalDate,
  tags: Set<String>,
  estimatedHours: Double,
): Task {
  val task = Task(title, description, priority, dueDate, tags, estimatedHours)
  if (!validator(task)) throw IllegalArgumentException("Invalid task")
  logger.info("Task created: ${task.title} [${locale.language}]")
  return task
}

fun sample10() {
  val logger = Logger.getLogger("TaskLogger")
  val validator: (Task) -> Boolean = { task ->
    task.title.isNotBlank() && task.dueDate.isAfter(LocalDate.now())
  }

  // Каррируем функцию создания задачи
  val curriedCreate = ::createTask.curried()

  val createEnglishTask = curriedCreate
    .invoke(Locale.UK)
    .invoke(logger)
    .invoke(validator)

  val createGermanTask = curriedCreate
    .invoke(Locale.GERMANY)
    .invoke(logger)
    .invoke(validator)

  val createUrgentTask = { title: String, desc: String, hours: Double ->
    createEnglishTask(title)(desc)(Priority.HIGH)(LocalDate.now().plusDays(1))(setOf("urgent"))(
      hours
    )
  }

  val createLearningTask = { title: String, hours: Double ->
    createEnglishTask(title)("Learn $title")(Priority.MEDIUM)(
      LocalDate.now().plusDays(7)
    )(setOf("education"))(hours)
  }

  // 3. Используем специализированные конструкторы
  val bugFixTask = createUrgentTask("Fix critical bug", "Security issue in auth", 4.0)
  val kotlinTask = createLearningTask("Kotlin Coroutines", 8.0)
  val germanTask = createGermanTask("Dokument schreiben")("Wichtiges Dokument")(Priority.MEDIUM)(
    LocalDate.now().plusDays(3)
  )(setOf())(2.0)

  println("Bug fix task: $bugFixTask")
  println("Learning task: $kotlinTask")
  println("German task: $germanTask")

  // 4. Частичное применение для повторяющихся параметров
  val createStandardTask = { title: String, desc: String, priority: Priority ->
    createEnglishTask(title)(desc)(priority)(LocalDate.now().plusDays(14))(emptySet())(1.0)
  }

  val standardTask1 = createStandardTask("Code review", "Review PR #123", Priority.MEDIUM)
  val standardTask2 = createStandardTask("Update docs", "Update README", Priority.LOW)

  println("Standard task 1: $standardTask1")
  println("Standard task 2: $standardTask2")
}