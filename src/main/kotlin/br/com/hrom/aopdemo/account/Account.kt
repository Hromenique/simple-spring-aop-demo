package br.com.hrom.aopdemo.account

import br.com.hrom.aopdemo.aspects.LogMe
import java.math.BigDecimal
import kotlin.jvm.Throws

data class Account(val id: Int, val owner: String, var balance: BigDecimal) {

    @LogMe
    @Throws(IllegalArgumentException::class)
    fun withdraw(amount: BigDecimal): BigDecimal {
        require(amount > 0.toBigDecimal()) { "Invalid value $amount" }
        require(amount <= balance) { "Insuficient balance $balance" }

        balance -= amount
        return amount
    }
}