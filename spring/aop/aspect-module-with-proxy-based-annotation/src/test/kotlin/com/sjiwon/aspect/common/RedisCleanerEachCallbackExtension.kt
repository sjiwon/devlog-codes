package com.sjiwon.aspect.common

import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension

class RedisCleanerEachCallbackExtension : AfterEachCallback {
    override fun afterEach(context: ExtensionContext) =
        SpringExtension
            .getApplicationContext(context)
            .getBean(RedisCleaner::class.java)
            .cleanUpRedis()
}
