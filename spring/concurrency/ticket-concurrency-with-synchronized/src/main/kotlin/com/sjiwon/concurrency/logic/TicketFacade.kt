package com.sjiwon.concurrency.logic

import org.springframework.stereotype.Component

@Component
class TicketFacade(
    private val target: TicketV3Service,
) {
    @Synchronized
    fun invoke(
        ticketId: Long,
        amount: Int,
    ) {
        target.purchase(ticketId, amount)
    }
}
