package de.activegroup;

/*
 * Ein Duschprodukt:
 * - Seife -ODER
 * - Shampoo -ODER-
 * - Duschgel, bestehend aus Seife und Shampoo
 */
public sealed interface ShowerProduct {
    record Soap(double pH) implements ShowerProduct {}
    record Shampoo(HairType hairType) implements ShowerProduct {}
    record ShowerGel(ShowerProduct product1, ShowerProduct product2)
            implements ShowerProduct {}
}
