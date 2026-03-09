package de.activegroup;

/*
 * Einflussbereich von Schiffen/Flotten
 *
 * Eine Flotte ist ...
 * - Schiff mit "Kreiskanone" ODER
 * - Schiff mit "Quadratkanone" ODER
 * - Verband aus zwei Flotten
 *
 * Ist ein Punkt innerhalb oder außerhalb des Einflussbereichs
 * einer Flotte?
 */

public sealed interface Flotte {
    record Kreiskanone(Point position, double reichweite) implements Flotte {}
    record Quadratkanone() implements Flotte {}
    record Verband(Flotte flotte1, Flotte flotte2) implements Flotte {}
}
