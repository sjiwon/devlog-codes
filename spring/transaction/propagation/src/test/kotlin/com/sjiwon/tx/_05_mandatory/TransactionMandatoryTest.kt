package com.sjiwon.tx._05_mandatory

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.IllegalTransactionStateException

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class TransactionMandatoryTest(
    private val main: MandatoryMainComponent,
) {
    @Test
    fun `기존 트랜잭션 X`() {
//        main.case1()
        shouldThrow<IllegalTransactionStateException> { main.case1() }
    }

    @Test
    fun `기존 트랜잭션 O`() {
        shouldNotThrowAny { main.case2() }
    }
}
