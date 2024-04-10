package com.sjiwon.aspect

import com.sjiwon.IntegrationTest
import com.sjiwon.aspect.common.DatabaseCleanerEachCallbackExtension
import com.sjiwon.aspect.common.RedisCleanerEachCallbackExtension
import com.sjiwon.aspect.common.RedisTestContainers
import com.sjiwon.aspect.domain.MemberRepository
import io.kotest.matchers.collections.shouldHaveSize
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(
    initializers = [
        RedisTestContainers.Initializer::class,
    ],
)
@ExtendWith(
    DatabaseCleanerEachCallbackExtension::class,
    RedisCleanerEachCallbackExtension::class,
)
@AutoConfigureMockMvc
@IntegrationTest
class TransactionTest(
    private val mockMvc: MockMvc,
    private val memberRepository: MemberRepository,
    private val redisTemplate: RedisTemplate<String, Any>,
) {
    @Nested
    @DisplayName("RDB - @Aspect + @Tranactional 테스트")
    inner class Rdb {
        @Test
        fun `1) @Around + @Transactional`() {
            execute("/rdb/typeA")
            memberRepository.findAll() shouldHaveSize 3
        }

        @Test
        fun `2) @Around + SeparateRdbTransactionalComponent`() {
            execute("/rdb/typeB")
            memberRepository.findAll() shouldHaveSize 0
        }

        @Test
        fun `3) @Around + TransactionTemplate`() {
            execute("/rdb/typeC")
            memberRepository.findAll() shouldHaveSize 0
        }
    }

    @Nested
    @DisplayName("Redis - @Aspect + @Tranactional 테스트")
    inner class Redis {
        @Test
        fun `1) @Around + @Transactional + nonTxTemplate`() {
            execute("/redis/typeA")
            redisTemplate.keys("*") shouldHaveSize 3
        }

        @Test
        fun `2) @Around + @Transactional + txTemplate`() {
            execute("/redis/typeB")
            redisTemplate.keys("*") shouldHaveSize 3
        }

        @Test
        fun `3) @Around + SeparateRedisTransactionalComponentA`() {
            execute("/redis/typeC")
            redisTemplate.keys("*") shouldHaveSize 3
        }

        @Test
        fun `4) @Around + SeparateRedisTransactionalComponentB`() {
            execute("/redis/typeD")
            redisTemplate.keys("*") shouldHaveSize 0
        }
    }

    private fun execute(url: String) {
        try {
            mockMvc.post(url)
        } catch (_: Exception) {
        }
    }
}
