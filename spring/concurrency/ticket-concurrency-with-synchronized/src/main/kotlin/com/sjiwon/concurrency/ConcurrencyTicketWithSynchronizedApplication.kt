package com.sjiwon.concurrency

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ConcurrencyTicketWithSynchronizedApplication

fun main(args: Array<String>) {
    runApplication<ConcurrencyTicketWithSynchronizedApplication>(*args)
}
