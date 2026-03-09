package de.activegroup;

public sealed interface Animal {

    default public Animal runOver() {
        switch (this)
    }

    /*
     * Gürteltier hat folgende Eigenschaften:
     * - lebendig oder tot        -UND-
     * - Gewicht
     * zusammengesetzte Daten / Produkt
    */
    record Dillo(Liveness liveness, double weight) implements Animal {
        // @Override
        public Dillo runOver() {
            return new Dillo(Liveness.DEAD, weight);
        }
    }

    /*
     * Eine Klapperschlange hat:
     * - Länge  UND
     * - Dicke
     */
    public record Snake(double length, double width) implements Animal {
        // @Override
        public Snake runOver() {
            return new Snake(this.length, 0);
        }
    }

}
