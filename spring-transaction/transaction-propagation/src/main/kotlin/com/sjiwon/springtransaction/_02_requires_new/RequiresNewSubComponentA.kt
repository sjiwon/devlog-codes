package com.sjiwon.springtransaction._02_requires_new

import com.sjiwon.springtransaction.TxException
import com.sjiwon.springtransaction.logger
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class RequiresNewSubComponentA {
    private val log: Logger = logger()

    @Transactional
    fun commit() {
        log.info("===== SubA(REQUIRED) BEGIN =====")
        log.info(">> SubA 로직 진행...")
        log.info("===== SubA(REQUIRED) COMMIT =====")
    }

    @Transactional
    fun rollback() {
        log.info("===== SubA(REQUIRED) BEGIN =====")
        log.info(">> SubA 로직 진행...")
        log.info("===== SubA(REQUIRED) ROLLBACK =====")

        throw TxException()
    }
}