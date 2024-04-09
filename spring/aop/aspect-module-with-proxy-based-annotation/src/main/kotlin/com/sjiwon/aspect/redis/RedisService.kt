package com.sjiwon.aspect.redis

import org.springframework.stereotype.Service

@Service
class RedisService {
    @ExtractCommonLogicRedisTxTypeA
    fun typeA() {
    }

    @ExtractCommonLogicRedisTxTypeB
    fun typeB() {
    }

    @ExtractCommonLogicRedisTxTypeC
    fun typeC() {
    }

    @ExtractCommonLogicRedisTxTypeD
    fun typeD() {
    }
}
