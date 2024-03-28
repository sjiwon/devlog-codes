package com.sjiwon.tx._07_nested

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import jakarta.persistence.EntityManagerFactory
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.NestedTransactionNotSupportedException
import org.springframework.transaction.PlatformTransactionManager

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class TransactionNestedWithJpaTest(
    private val main: NestedMainComponent,
) {
    @TestConfiguration
    class Config {
        @Bean
        fun txManager(emf: EntityManagerFactory): PlatformTransactionManager {
            return JpaTransactionManager(emf)
        }
    }

    @Test
    fun `기존 트랜잭션 X`() {
        shouldNotThrowAny { main.case1() }
    }

    @Test
    fun `기존 트랜잭션 O - Main(Commit) & Sub(Rollback)`() {
        shouldThrow<NestedTransactionNotSupportedException> { main.case2(jpaTx = true) }
    }

    @Test
    fun `기존 트랜잭션 O - Main(Rollback) & Sub(Commit)`() {
        shouldThrow<NestedTransactionNotSupportedException> { main.case3() }
    }
}
