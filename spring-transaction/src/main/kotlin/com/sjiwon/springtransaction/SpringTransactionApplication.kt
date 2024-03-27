package com.sjiwon.springtransaction

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringTransactionApplication

fun main(args: Array<String>) {
    val context = runApplication<SpringTransactionApplication>(*args)
    context.getBean(TransactionDef::class.java).execute()
}
