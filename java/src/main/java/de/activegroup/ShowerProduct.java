package de.activegroup;

/*
 * Ein Duschprodukt:
 * - Seife -ODER
 * - Shampoo -ODER-
 * - Duschgel, bestehend aus Seife und Shampoo
 */
// Kombinatormodell
public sealed interface ShowerProduct {

    // Seifenanteil ermitteln 1 für "100% Seife"
    default double soapProportion() {
        return switch (this) {
            case Soap(double pH) -> 1.0;
            case Shampoo(HairType hairType) -> 0.0;
            case ShowerGel(ShowerProduct product1, ShowerProduct product2) ->
                    (product1.soapProportion() + product2.soapProportion()) / 2.0;
        };
    }

    record Soap(double pH) implements ShowerProduct {}
    record Shampoo(HairType hairType) implements ShowerProduct {}
    // Selbstbezüge: ShowerProduct wird in seiner eigenen Definition
    // verwendet
    // Kombinator: aus zwei Duschprodukten eins machen
    record ShowerGel(ShowerProduct product1, ShowerProduct product2)
            implements ShowerProduct {}
}
