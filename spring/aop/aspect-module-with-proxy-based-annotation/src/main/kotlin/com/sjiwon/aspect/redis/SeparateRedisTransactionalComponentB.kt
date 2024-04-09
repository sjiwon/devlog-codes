package com.sjiwon.aspect.redis

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SeparateRedisTransactionalComponentB(
    @Qualifier("txTemplate") private val template: StringRedisTemplate,
) {
    @Transactional
    fun invoke() {
        val executor = template.opsForValue()
        executor.set("separate-component-txTemplate-1", "success")
        executor.set("separate-component-txTemplate-2", "success")
        executor.set("separate-component-txTemplate-3", "success")
        throw RuntimeException()
    }
}
