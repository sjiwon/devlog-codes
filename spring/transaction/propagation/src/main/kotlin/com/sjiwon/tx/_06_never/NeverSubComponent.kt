package com.sjiwon.tx._06_never

import com.sjiwon.tx.logger
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class NeverSubComponent {
    private val log: Logger = logger()

    @Transactional(propagation = Propagation.NEVER)
    fun execute() {
        log.info("===== Sub(NEVER) BEGIN =====")
        log.info(">> Sub 로직 진행...")
        log.info("===== Sub(NEVER) COMMIT =====")
    }
}
