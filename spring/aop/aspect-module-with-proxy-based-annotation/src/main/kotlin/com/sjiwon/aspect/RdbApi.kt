package com.sjiwon.aspect

import com.sjiwon.aspect.rdb.RdbService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RdbApi(
    private val rdbService: RdbService,
) {
    @PostMapping("/rdb/typeA")
    fun typeA(): String {
        rdbService.typeA()
        return "ok"
    }

    @PostMapping("/rdb/typeB")
    fun typeB(): String {
        rdbService.typeB()
        return "ok"
    }

    @PostMapping("/rdb/typeC")
    fun typeC(): String {
        rdbService.typeC()
        return "ok"
    }
}
