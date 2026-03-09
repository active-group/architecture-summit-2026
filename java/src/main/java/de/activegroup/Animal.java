package de.activegroup;

public sealed interface Animal {

    /*
     * Tier auf dem texanischen Highyway:
     * - Gürteltier      ODER
     * - Klapperschlange
     * Summe
     */

    default public Animal runOver() {
        return switch (this) {
            case Dillo(Liveness l, double w) ->
                new Dillo(Liveness.DEAD, w);
            case Snake(double length, double width) ->
                new Snake(length, 0);
        };
    }

    default public Animal feed(double amount) {
        return switch (this) {
          case Dillo(Liveness liveness, double weight) ->
                  switch (liveness) {
                    case Liveness.ALIVE ->
                            new Dillo(liveness, weight + amount);
                    case Liveness.DEAD ->
                            this;
                  };
          case Snake(double length, double width) ->
              new Snake(length, width + amount);
        };
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
