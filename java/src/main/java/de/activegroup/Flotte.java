package de.activegroup;

/*
 * Einflussbereich von Schiffen/Flotten
 *
 * Eine Flotte ist ...
 * - Schiff mit "Kreiskanone" ODER
 * - Schiff mit "Quadratkanone" ODER
 * - Verband aus zwei Flotten
 *
 * Ist eine Position innerhalb oder außerhalb des Einflussbereichs
 * einer Flotte?
 */

public sealed interface Flotte {

    record Kreiskanone(Position mitte, double reichweite)
            implements Flotte {}
    record Quadratkanone(Position mitte, double kantenlänge)
            implements Flotte {}

    // aus 2 wird 1
    // x + y
    // x + (y + z) = (x + y) + z  Assoziativität
    // x * y
    // x * (y * z) = (x * y) * z
    // x && y
    // x || y
    record Verband(Flotte flotte1, Flotte flotte2) implements Flotte {}

    // Semantik
    default boolean istInnerhalb(Position position) {
        return switch(position) {
            case Position(double positionX, double positionY) -> switch (this) {
                case Kreiskanone(Position(double mitteX, double mitteY), double reichweite) -> {
                    double abstand = Math.sqrt(Math.pow(mitteX - positionX, 2) +
                            Math.pow(mitteY - positionY, 2));
                    yield abstand <= reichweite;
                }
                case Quadratkanone(Position(double mitteX, double mitteY), double kantenlänge) -> {
                    double minX = mitteX - kantenlänge / 2;
                    double maxX = mitteX + kantenlänge / 2;
                    double minY = mitteY - kantenlänge / 2;
                    double maxY = mitteY + kantenlänge / 2;
                    yield positionX >= minX && positionX <= maxX &&
                            positionY >= minY && positionY <= maxY;
                }
                case Verband(Flotte flotte1, Flotte flotte2) -> flotte1.istInnerhalb(position) ||
                        flotte2.istInnerhalb(position);
            };
        };
    }
}
