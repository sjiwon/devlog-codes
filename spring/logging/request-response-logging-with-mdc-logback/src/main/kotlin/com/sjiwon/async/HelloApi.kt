package com.sjiwon.async

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloApi(
    private val helloService: HelloService,
) {
    @GetMapping("/api/v1/async")
    fun async(): String {
        helloService.async()
        return "ok"
    }

    @GetMapping("/api/v1/event")
    fun event(): String {
        helloService.event()
        return "ok"
    }
}
