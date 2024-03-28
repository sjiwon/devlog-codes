package com.sjiwon.tx._06_never

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.IllegalTransactionStateException

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class TransactionNeverTest(
    private val main: NeverMainComponent,
) {
    @Test
    fun `기존 트랜잭션 X`() {
        shouldNotThrowAny { main.case1() }
    }

    @Test
    fun `기존 트랜잭션 O`() {
        shouldThrow<IllegalTransactionStateException> { main.case2() }
    }
}
