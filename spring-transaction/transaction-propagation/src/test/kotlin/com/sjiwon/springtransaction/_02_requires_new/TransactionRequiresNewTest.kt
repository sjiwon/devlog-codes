package com.sjiwon.springtransaction._02_requires_new

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.UnexpectedRollbackException

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class TransactionRequiresNewTest(
    private val main: RequiresNewMainComponent,
) {
    @Test
    fun `SubA(Commit) & SubB(Rollback)`() {
        shouldNotThrowAny { main.case1() }
    }

    @Test
    fun `SubA(Rollback) & SubB(Rollback)`() {
        shouldThrow<UnexpectedRollbackException> { main.case2() }
    }
}
