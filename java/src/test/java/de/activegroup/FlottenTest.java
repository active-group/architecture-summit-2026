package de.activegroup;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlottenTest {
    Position p1 = new Position(3, 5);
    Position p2 = new Position(6, 4);
    Flotte schiff1 = new Flotte.Kreiskanone(p1, 4);
    Flotte schiff2 = new Flotte.Quadratkanone(p2, 2);
    Flotte verband1 = new Flotte.Verband(schiff1, schiff2);
    Flotte schiff3 = new Flotte.Kreiskanone(new Position(20, 20), 2);
    Flotte verband2 = new Flotte.Verband(verband1, schiff3);
    Flotte verband3 = new Flotte.Verband(schiff1,
            new Flotte.Verband(schiff2, schiff3));
    // p1 ist in schiff1, verband1
    // p2 ist in schiff2, verband1
    // (20, 20) ist nicht mehr drin
    @Test
    public void testeHalt() {
        System.out.println(verband2);
        System.out.println(verband3);
        assertTrue(verband1.istInnerhalb(p1));
        assertTrue(verband1.istInnerhalb(p2));
        assertFalse(verband1.istInnerhalb(new Position(20, 20)));
    }
}
