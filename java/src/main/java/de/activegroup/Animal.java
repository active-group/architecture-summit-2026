package de.activegroup;

public sealed interface Animal {
    /*
     * Gürteltier hat folgende Eigenschaften:
     * - lebendig oder tot        -UND-
     * - Gewicht
     * zusammengesetzte Daten / Produkt
    */
    record Dillo(Liveness liveness, double weight) {
        public Dillo runOver() {
            return new Dillo(Liveness.DEAD, weight);
        }
    }

}
