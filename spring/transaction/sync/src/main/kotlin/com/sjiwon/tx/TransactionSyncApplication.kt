package com.sjiwon.tx

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TransactionSyncApplication

fun main(args: Array<String>) {
    val context = runApplication<TransactionSyncApplication>(*args)
    context.getBean(TransactionDef::class.java).execute()
}
