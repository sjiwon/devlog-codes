package com.sjiwon.aspect.common

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisCleaner(
    private val redisTemplate: RedisTemplate<String, Any>,
) {
    fun cleanUpRedis() {
        redisTemplate.connectionFactory
            ?.connection
            ?.serverCommands()
            ?.flushAll()
    }
}
