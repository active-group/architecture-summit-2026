package de.activegroup

import kotlin.contracts.ContractBuilder

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

data object Zero: Contract

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

fun Direction.reverse(): Direction =
    when (this) {
        Direction.LONG -> Direction.SHORT
        Direction.SHORT -> Direction.LONG
    }

data class Reverse(val contract: Contract): Contract

data class And(val contract1: Contract,
    val contract2: Contract): Contract

// Halbgruppe:
// Assoziativität
// (a + b) + c = a + (b + c)
// (a * b) * c = a * (b * c)

// Monoid:
// "0 ist das neutrale Element von +"
// "1 ist das neutrale Element von *"
// And(Zero, c) = And(c, Zero) = c
// a + 0 = 0 + a = a
// a * 1 = 1 * a = a

// "smart constructor"
fun and(contract1: Contract,
        contract2: Contract): Contract =
    when (contract1) {
        is Zero -> contract2
        else ->
            when (contract2) {
                is Zero -> contract1
                else -> And(contract1, contract2)
            }
    }


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

data class Payment(val date: Date,
    val direction: Direction,
    val amount: Amount,
    val currency: Currency) {
    fun reverse(): Payment =
        Payment(date, direction.reverse(),
            amount, currency)
    fun scale(factor: Amount): Payment =
        Payment(date, direction,
            amount * factor,
            currency)
}

// Semantik von Verträgen
// alle Zahlungen bis now + "Residualvertrag"
fun semantics(contract: Contract, now: Date)
  : Pair<List<Payment>, Contract> =
    when (contract) {
        is Zero -> Pair(listOf(), Zero)
        is One ->
            Pair(listOf(Payment(now, Direction.LONG, 1.0,
                          contract.currency)),
                Zero)
        is Scaled -> {
            val (payments, residualContract) =
                semantics(contract.contract, now)
            Pair(payments.map { payment -> payment.scale(contract.amount)},
                 Scaled(contract.amount, residualContract))
        }
        is Later ->
            if (now < contract.date) // Datum noch nicht erreicht
                Pair(listOf(), contract)
            else // Datum ist erreicht
                semantics(contract.contract, now)
        is Reverse -> {
            val (payments, residualContract)
                    = semantics(contract.contract, now)
            Pair(payments.map { payment -> payment.reverse() },
                 Reverse(residualContract))
        }
        is And -> {
            val (payments1, residualContract1) =
                semantics(contract.contract1, now)
            val (payments2, residualContract2) =
                semantics(contract.contract2, now)
            Pair(payments1 + payments2,
                and(residualContract1, residualContract2))
        }
    }

val c5 = Scaled(100.0,
    And(One(Currency.EUR),
        Later(xmas, One(Currency.EUR))))