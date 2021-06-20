package br.com.hrom.aopdemo.account

import br.com.hrom.aopdemo.aspects.CacheMe
import br.com.hrom.aopdemo.aspects.HowLongDoITake
import br.com.hrom.aopdemo.aspects.LogMe
import org.springframework.stereotype.Component
import java.math.BigDecimal
import kotlin.jvm.Throws

@Component
class AccountService {

    private val accounts = mapOf(
            1 to Account(1, "Paul", 1000.toBigDecimal()),
            2 to Account(2, "Mary", 1000.toBigDecimal()),
            3 to Account(3, "Jonh", 1000.toBigDecimal()),
            4 to Account(4, "Robert", 1000.toBigDecimal())
    )

    @CacheMe("account")
    @LogMe
    @HowLongDoITake
    @Throws(IllegalArgumentException::class)
    fun getAccount(id: Int): Account {
        Thread.sleep(1000) // doing something
        return accounts[id] ?: throw IllegalArgumentException("Account $id not found")
    }

    @LogMe(logError = true)
    @HowLongDoITake
    @Throws(IllegalArgumentException::class)
    fun withdraw(id: Int, amount: BigDecimal) = getAccount(id).withdraw(amount)
}