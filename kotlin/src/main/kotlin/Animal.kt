package de.activegroup

/*
 * Tier auf dem texanischen Highway:
 * - Gürteltier  ODER
 * - Klapperschlange
 * Summe
 *
 * Gürteltier hat folgende Eigenschaften:
 * - lebendig oder tot      -UND-
 * - Gewicht
 * Produkt
 *
 * Klapperschlange hat folgende Eigenschaften:
 * - Länge
 * - Dicke
 */

enum class Liveness {
    ALIVE, DEAD
}

typealias Weight = Double

sealed interface Animal

// Zustand des Gürteltiers zu einem Zeitpunkt
data class Dillo(val liveness: Liveness,
    val weight: Weight): Animal
data class Snake(val length: Double, val width: Double): Animal

// lebendiges Gürteltier, 10kg
val dillo1 = Dillo(Liveness.ALIVE, 10.0)
// totes Gürteltier, 8kg
val dillo2 = Dillo(Liveness.DEAD, 8.0)

// Gürteltier überfahren
fun runOverDillo(dillo: Dillo): Dillo =
    Dillo(Liveness.DEAD, dillo.weight)

fun runOverAnimal(animal: Animal): Animal =
    when (animal) {
        is Dillo -> Dillo(Liveness.DEAD, animal.weight)
        is Snake -> Snake(animal.length, 0.0)
    }