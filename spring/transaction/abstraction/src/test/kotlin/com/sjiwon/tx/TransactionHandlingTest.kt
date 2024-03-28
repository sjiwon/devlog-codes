package com.sjiwon.tx

import com.sjiwon.tx.common.DatabaseCleanerEachCallbackExtension
import com.sjiwon.tx.logic.JdbcComponent
import com.sjiwon.tx.logic.JpaComponent
import com.sjiwon.tx.model.BucketRepository
import com.sjiwon.tx.model.MemberRepository
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldHaveSize
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@SpringBootTest
@ExtendWith(DatabaseCleanerEachCallbackExtension::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class TransactionHandlingTest(
    private val jdbcComponent: JdbcComponent,
    private val jpaComponent: JpaComponent,
    private val memberRepository: MemberRepository,
    private val bucketRepository: BucketRepository,
) {
    @Test
    fun `JdbcComponent - 예외 발생 X`() {
        jdbcComponent.logic(exception = false)

        assertSoftly {
            memberRepository.findAll() shouldHaveSize 1
            bucketRepository.findAll() shouldHaveSize 1
        }
    }

    @Test
    fun `JdbcComponent - 예외 발생 O`() {
        jdbcComponent.logic(exception = true)

        assertSoftly {
            memberRepository.findAll() shouldHaveSize 0
            bucketRepository.findAll() shouldHaveSize 0
        }
    }

    @Test
    fun `JpaComponent - 예외 발생 X`() {
        jpaComponent.logic(exception = false)

        assertSoftly {
            memberRepository.findAll() shouldHaveSize 1
            bucketRepository.findAll() shouldHaveSize 1
        }
    }

    @Test
    fun `JpaComponent - 예외 발생 O`() {
        jpaComponent.logic(exception = true)

        assertSoftly {
            memberRepository.findAll() shouldHaveSize 0
            bucketRepository.findAll() shouldHaveSize 0
        }
    }
}
