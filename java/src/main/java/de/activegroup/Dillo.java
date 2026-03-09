package de.activegroup;

/*
 * Tier auf dem texanischen Highyway:
 * - Gürteltier      ODER
 * - Klapperschlange
 * Summe
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
