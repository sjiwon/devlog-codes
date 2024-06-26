package com.sjiwon.concurrency.logic

import com.sjiwon.IntegrationTest
import com.sjiwon.concurrency.logic.common.DatabaseCleanerEachCallbackExtension
import com.sjiwon.concurrency.model.Ticket
import com.sjiwon.concurrency.model.TicketRepository
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@ExtendWith(DatabaseCleanerEachCallbackExtension::class)
@IntegrationTest
class TicketV4ServiceTest(
    private val sut: TicketV4Service,
    private val ticketRepository: TicketRepository,
) {
    companion object {
        private const val THREAD_COUNT = 20
    }

    private lateinit var executorService: ExecutorService
    private lateinit var countDownLatch: CountDownLatch
    private lateinit var ticket: Ticket

    @BeforeEach
    fun setUp() {
        executorService = Executors.newFixedThreadPool(THREAD_COUNT)
        countDownLatch = CountDownLatch(THREAD_COUNT)
        ticket = ticketRepository.save(Ticket(stock = 100))
    }

    @Test
    fun `20명의 참가자가 티겟을 5장씩 구매한다`() {
        // when
        for (i in 1..THREAD_COUNT) {
            executorService.submit {
                try {
                    sut.purchase(ticketId = ticket.id, amount = 5)
                } finally {
                    countDownLatch.countDown()
                }
            }
        }
        executorService.shutdown()
        countDownLatch.await()

        // then
        ticketRepository.findByIdOrNull(ticket.id)!!.stock shouldBe 0
    }
}
