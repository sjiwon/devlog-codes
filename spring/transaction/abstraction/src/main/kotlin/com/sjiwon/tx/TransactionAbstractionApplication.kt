package com.sjiwon.tx

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TransactionAbstractionApplication

fun main(args: Array<String>) {
    runApplication<TransactionAbstractionApplication>(*args)
}
