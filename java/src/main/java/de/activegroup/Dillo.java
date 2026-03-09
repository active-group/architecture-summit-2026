package de.activegroup;

/*
 * Tiere auf dem texanischen Highyway:
 *
 * Datendefinition:
 * Gürteltier hat folgende Eigenschaften:
 * - lebendig oder tot        -UND-
 * - Gewicht
 * zusammengesetzte Daten / Produkt
 */

public record Dillo(Liveness liveness, double weight) {
    public Dillo runOver() {
        return new Dillo(Liveness.DEAD, weight);
    }
}
