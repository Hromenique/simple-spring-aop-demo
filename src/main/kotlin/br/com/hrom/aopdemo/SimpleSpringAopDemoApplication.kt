package br.com.hrom.aopdemo

import br.com.hrom.aopdemo.account.AccountService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleSpringAopDemoApplication(private val accountService: AccountService) : CommandLineRunner {

    override fun run(vararg args: String?) {
        println("-> takes account ${accountService.getAccount(1)}")
        println("-> takes ${accountService.withdraw(id = 2, amount = 200.toBigDecimal())}")

        runCatching { accountService.getAccount(10) }.onFailure { ex -> println("-> error: ${ex.message}") }
        runCatching { accountService.withdraw(2, 50000.toBigDecimal()) }.onFailure { ex -> println("-> error: ${ex.message}") }

        println("-> takes account ${accountService.getAccount(3)}")
        println("-> takes again account ${accountService.getAccount(3)}")

        println("-> takes account ${accountService.getAccount(4)}")
        println("-> takes again account ${accountService.getAccount(4)}")
        println("-> takes again account ${accountService.getAccount(4)}")
    }
}

fun main(args: Array<String>) {
    runApplication<SimpleSpringAopDemoApplication>(*args)
}
