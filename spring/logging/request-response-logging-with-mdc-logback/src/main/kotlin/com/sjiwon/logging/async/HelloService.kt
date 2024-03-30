package com.sjiwon.logging.async

import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class HelloService(
    private val eventPublisher: ApplicationEventPublisher,
) {
    @Async
    fun async() {
    }

    fun event() {
        eventPublisher.publishEvent(HelloEvent(id = 1L))
    }
}
