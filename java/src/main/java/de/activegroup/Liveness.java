package de.activegroup;

/*
 * Eine Lebendigkeit ist eins der folgenden:
 * - lebend  ODER
 * - tot
 * Fallunterscheidung / gemischte Daten / Summe
 * hier speziell: Aufzählung
 */
public sealed interface Liveness {
    record Alive() implements Liveness {}
    record Dead() implements Liveness {}
    Liveness ALIVE = new Alive();
    Liveness DEAD = new Dead();
}
