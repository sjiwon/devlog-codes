package com.sjiwon.tx._04_not_supported

import com.sjiwon.logger
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class NotSupportedSubComponent {
    private val log: Logger = logger()

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun execute() {
        log.info("===== Sub(NOT_SUPPORTED) BEGIN =====")
        log.info(">> Sub 로직 진행...")
        log.info("===== Sub(NOT_SUPPORTED) COMMIT =====")
    }
}
