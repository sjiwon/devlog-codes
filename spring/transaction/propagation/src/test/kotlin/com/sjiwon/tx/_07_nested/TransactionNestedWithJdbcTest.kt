package com.sjiwon.tx._07_nested

import com.sjiwon.tx.TxException
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.support.JdbcTransactionManager
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class TransactionNestedWithJdbcTest(
    private val main: NestedMainComponent,
) {
    @TestConfiguration
    class Config {
        @Bean
        fun txManager(datasource: DataSource): PlatformTransactionManager {
            return JdbcTransactionManager(datasource)
        }
    }

    @Test
    fun `기존 트랜잭션 X`() {
        shouldNotThrowAny { main.case1() }
    }

    @Test
    fun `기존 트랜잭션 O - Main(Commit) & Sub(Rollback)`() {
        shouldNotThrowAny { main.case2(jpaTx = false) }
    }

    @Test
    fun `기존 트랜잭션 O - Main(Rollback) & Sub(Commit)`() {
        shouldThrow<TxException> { main.case3() }
    }
}
