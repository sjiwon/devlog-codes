package com.sjiwon.concurrency.api

import com.sjiwon.concurrency.logic.TicketV5Service
import com.sjiwon.concurrency.logic.TicketV6Service
import com.sjiwon.concurrency.model.Ticket
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TicketApi(
    private val environment: Environment,
    private val serviceV5: TicketV5Service,
    private val serviceV6: TicketV6Service,
) {
    private var visitCount = 0

    @GetMapping("/api/health")
    fun health(): Map<String, Any> = mapOf(
        "visitCount" to visitCount++,
        "server" to getServer()
    )

    data class Request(
        val amount: Int,
    )

    data class Response(
        val server: String,
        val ticket: Ticket,
    )

    @PostMapping("/api/v1/tickets/{ticketId}/purchase")
    fun purchaseV1(
        @PathVariable ticketId: Long,
        @RequestBody request: Request,
    ): Response {
        val ticket = serviceV5.purchase(ticketId, request.amount)
        return Response(
            server = getServer(),
            ticket = ticket,
        )
    }

    @PostMapping("/api/v2/tickets/{ticketId}/purchase")
    fun purchaseV2(
        @PathVariable ticketId: Long,
        @RequestBody request: Request,
    ): Response {
        val ticket = serviceV6.purchase(ticketId, request.amount)
        return Response(
            server = getServer(),
            ticket = ticket,
        )
    }

    private fun getServer(): String = environment.getProperty("server", "?")

    @ExceptionHandler
    fun handle(ex: RuntimeException): String = ex.message ?: "empty"
}
