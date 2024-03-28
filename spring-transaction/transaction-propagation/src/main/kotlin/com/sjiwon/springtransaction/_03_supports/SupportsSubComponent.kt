package com.sjiwon.springtransaction._03_supports

import com.sjiwon.springtransaction.logger
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class SupportsSubComponent {
    private val log: Logger = logger()

    @Transactional(propagation = Propagation.SUPPORTS)
    fun execute() {
        log.info("===== Sub(SUPPORTS) BEGIN =====")
        log.info(">> Sub 로직 진행...")
        log.info("===== Sub(SUPPORTS) COMMIT =====")
    }
}
