package com.sjiwon.tx._05_mandatory

import com.sjiwon.tx.logger
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class MandatorySubComponent {
    private val log: Logger = logger()

    @Transactional(propagation = Propagation.MANDATORY)
    fun execute() {
        log.info("===== Sub(MANDATORY) BEGIN =====")
        log.info(">> Sub 로직 진행...")
        log.info("===== Sub(MANDATORY) COMMIT =====")
    }
}
