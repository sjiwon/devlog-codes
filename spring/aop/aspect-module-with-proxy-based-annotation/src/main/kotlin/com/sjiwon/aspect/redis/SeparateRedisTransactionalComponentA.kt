package com.sjiwon.aspect.redis

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SeparateRedisTransactionalComponentA(
    @Qualifier("nonTxTemplate") private val template: StringRedisTemplate,
) {
    @Transactional
    fun invoke() {
        val executor = template.opsForValue()
        executor.set("separate-component-nonTxTemplate-1", "success")
        executor.set("separate-component-nonTxTemplate-2", "success")
        executor.set("separate-component-nonTxTemplate-3", "success")
        throw RuntimeException()
    }
}
