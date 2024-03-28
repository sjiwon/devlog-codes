package com.sjiwon.springtransaction._01_required

import com.sjiwon.springtransaction.TxException
import com.sjiwon.springtransaction.logger
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class RequiredSubComponent {
    private val log: Logger = logger()

    @Transactional
    fun commit() {
        log.info("===== Sub(REQUIRED) BEGIN =====")
        log.info(">> Sub 로직 진행...")
        log.info("===== Sub(REQUIRED) COMMIT =====")
    }

    @Transactional
    fun rollback() {
        log.info("===== Sub(REQUIRED) BEGIN =====")
        log.info(">> Sub 로직 진행...")
        log.info("===== Sub(REQUIRED) ROLLBACK =====")

        throw TxException()
    }
}
