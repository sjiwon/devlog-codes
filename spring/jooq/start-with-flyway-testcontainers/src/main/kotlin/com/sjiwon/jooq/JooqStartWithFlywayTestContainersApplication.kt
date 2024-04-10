package com.sjiwon.jooq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JooqStartWithFlywayTestContainersApplication

fun main(args: Array<String>) {
    runApplication<JooqStartWithFlywayTestContainersApplication>(*args)
}
