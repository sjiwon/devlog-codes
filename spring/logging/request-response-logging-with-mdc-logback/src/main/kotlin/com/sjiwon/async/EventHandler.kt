package com.sjiwon.async

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class EventHandler {
    @Async
    @EventListener
    fun execute(event: HelloEvent) {
    }
}
