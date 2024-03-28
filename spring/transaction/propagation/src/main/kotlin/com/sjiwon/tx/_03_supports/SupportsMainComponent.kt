package com.sjiwon.tx._03_supports

import com.sjiwon.logger
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SupportsMainComponent(
    private val sub: SupportsSubComponent,
) {
    private val log: Logger = logger()

    fun case1() {
        log.info("===== Main(No TX) BEGIN =====")
        log.info(">> Main 로직 진행...")
        sub.execute()
        log.info("===== Main(No TX) COMMIT =====")
    }

    @Transactional
    fun case2() {
        log.info("===== Main(No TX) BEGIN =====")
        log.info(">> Main 로직 진행...")
        sub.execute()
        log.info("===== Main(No TX) COMMIT =====")
    }
}
