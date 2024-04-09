package com.sjiwon.aspect

import com.sjiwon.aspect.redis.RedisService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RedisApi(
    private val redisService: RedisService,
) {
    @PostMapping("/redis/typeA")
    fun typeA(): String {
        redisService.typeA()
        return "ok"
    }

    @PostMapping("/redis/typeB")
    fun typeB(): String {
        redisService.typeB()
        return "ok"
    }

    @PostMapping("/redis/typeC")
    fun typeC(): String {
        redisService.typeC()
        return "ok"
    }

    @PostMapping("/redis/typeD")
    fun typeD(): String {
        redisService.typeD()
        return "ok"
    }
}
