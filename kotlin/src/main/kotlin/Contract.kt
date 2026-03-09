package de.activegroup

/* einfaches Beispiel:
 * Zero-Bond / zero-coupon bond
 * "Ich bekomme 100€ am 24.12.2026."
 *
 * Beispiel zerlegen in "atomare Bestandteile"/Ideen
 * - "Währung"
 * - "Betrag"
 * - "später"
 *
 */

/* Ein Zero-Bond hat:
 * - Betrag  -UND-
 * - Währung -UND-
 * - Datum
 */

typealias Amount = Double

enum class Currency {
    EUR, YEN, USD, GBP, CHF
}

typealias Date = String

sealed interface Contract

/*
data class ZeroCouponBond(val amount: Amount,
    val currency: Currency,
    val date: Date): Contract

val zcb1 = ZeroCouponBond(100.0, Currency.EUR,
    "2026-12-24" )
 */

data class One(val currency: Currency): Contract

// "Ich bekomme 1€ jetzt."
val c1 = One(Currency.EUR)

// "Ich bekomme 100€ jetzt."
data class Scaled(val amount: Amount,
                  val contract: Contract) // Selbstbezug
    : Contract

val c2 = Scaled(100.0, c1)