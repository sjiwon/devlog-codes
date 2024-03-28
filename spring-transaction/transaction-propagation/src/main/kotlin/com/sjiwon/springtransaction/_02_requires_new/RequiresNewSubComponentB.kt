package com.sjiwon.springtransaction._02_requires_new

import com.sjiwon.springtransaction.TxException
import com.sjiwon.springtransaction.logger
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class RequiresNewSubComponentB {
    private val log: Logger = logger()

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun commit() {
        log.info("===== SubB(REQUIRES_NEW) BEGIN =====")
        log.info(">> SubB 로직 진행...")
        log.info("===== SubB(REQUIRES_NEW) COMMIT =====")
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun rollback() {
        log.info("===== SubB(REQUIRES_NEW) BEGIN =====")
        log.info(">> SubB 로직 진행...")
        log.info("===== SubB(REQUIRES_NEW) ROLLBACK =====")

        throw TxException()
    }
}
