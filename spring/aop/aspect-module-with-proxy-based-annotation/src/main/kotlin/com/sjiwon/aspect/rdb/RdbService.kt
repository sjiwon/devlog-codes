package com.sjiwon.aspect.rdb

import org.springframework.stereotype.Service

@Service
class RdbService {
    @ExtractCommonLogicRdbTxTypeA
    fun typeA() {
    }

    @ExtractCommonLogicRdbTxTypeB
    fun typeB() {
    }

    @ExtractCommonLogicRdbTxTypeC
    fun typeC() {
    }
}
