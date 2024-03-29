package com.sjiwon.concurrency.logic

import com.sjiwon.concurrency.model.Ticket
import com.sjiwon.concurrency.model.TicketRepository
import com.sjiwon.logger
import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TicketV6Service(
    private val ticketRepository: TicketRepository,
) {
    private val log: Logger = logger()

    @Transactional
    fun purchase(
        ticketId: Long,
        amount: Int,
    ): Ticket {
        val ticket = ticketRepository.findByIdWithLock(ticketId)
            ?: throw RuntimeException("Ticket not found ... $ticketId")
        log.info("${Thread.currentThread().name} -> [Ticket${ticketId} 현재 보유량=${ticket.stock} & 구매 요청량=${amount}]")
        ticket.purchase(amount)
        return ticket
    }
}
