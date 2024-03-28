package com.sjiwon.springtransaction._01_required

import com.sjiwon.springtransaction.TxException
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.UnexpectedRollbackException

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class TransactionRequiredTest(
    private val main: RequiredMainComponent,
) {
    @Test
    fun `Main(Commit) & Sub(Commit)`() {
        shouldNotThrowAny { main.case1() }
    }

    @Test
    fun `Main(Commit) & Sub(Rollback)`() {
        shouldThrow<UnexpectedRollbackException> { main.case2() }
    }

    @Test
    fun `Main(Rollback) & Sub(Commit)`() {
        shouldThrow<TxException> { main.case3() }
    }
}
