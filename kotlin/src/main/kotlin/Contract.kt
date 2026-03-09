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
 * nach Selbstbezügen suchen
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
                  val contract: Contract) // Selbstbezug ... Kombinator
    : Contract

val c2 = Scaled(100.0, c1)

data class Later(val date: Date, val contract: Contract): Contract

enum class Direction {
    LONG, SHORT
}

data class Reverse(val contract: Contract): Contract

data class And(val contract1: Contract,
    val contract2: Contract): Contract

val xmas = "2026-12-24"
val zcb1 = Later("2026-12-24",
    Scaled(100.0,
        One(Currency.EUR)))

fun zeroCouponBond(amount: Amount, currency: Currency, date: Date) =
    Later(date, Scaled(amount, One(currency)))

val zcb1_ = zeroCouponBond(100.0, Currency.EUR,
    "2026-12-24")

/*
// Ich zahle Weihnachten 100$.
val c3 = Recipient(Direction.SHORT,
            zeroCouponBond(100.0, Currency.USD, xmas))

// Ich bekomme Weihnachten 100$.
val c4 = Recipient(Direction.LONG,
    zeroCouponBond(100.0, Currency.USD, xmas))

// Ich bekomme 100$.
val c5 = Recipient(Direction.SHORT, c3)
*/
val c3 = Reverse(
    zeroCouponBond(100.0, Currency.USD, xmas))

// Currency Swap:
// Weihnachten bekomme ich 100€ und
// Weihnachten zahle   ich 100$.
val fxSwap1 = And(zcb1, c3)

