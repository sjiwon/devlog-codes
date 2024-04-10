package com.sjiwon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LoggingRequestResponseWithMdcLogbackApplication

fun main(args: Array<String>) {
    runApplication<LoggingRequestResponseWithMdcLogbackApplication>(*args)
}
