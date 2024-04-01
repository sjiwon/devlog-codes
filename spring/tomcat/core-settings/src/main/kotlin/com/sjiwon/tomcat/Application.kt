package com.sjiwon.tomcat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@RestController
class Api {
    @GetMapping("/api")
    fun hello(): String {
        Thread.sleep(3000)
        return "Hello Thread World"
    }
}
