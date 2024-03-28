package com.sjiwon.tx._03_supports

import io.kotest.assertions.throwables.shouldNotThrowAny
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class TransactionSupportsTest(
    private val main: SupportsMainComponent,
) {
    @Test
    fun `기존 트랜잭션 X`() {
        shouldNotThrowAny { main.case1() }
    }

    @Test
    fun `기존 트랜잭션 O`() {
        shouldNotThrowAny { main.case2() }
    }
}
