package de.activegroup;

/*
 * Eine Klapperschlange hat:
 * - Länge  UND
 * - Dicke
 */
public record Snake(double length, double width) {
    public Snake runOver() {
        return new Snake(this.length, 0);
    }
}
