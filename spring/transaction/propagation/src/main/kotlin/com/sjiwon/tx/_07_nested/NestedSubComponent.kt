package com.sjiwon.tx._07_nested

import com.sjiwon.tx.TxException
import com.sjiwon.tx.logger
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class NestedSubComponent {
    private val log: Logger = logger()

    @Transactional(propagation = Propagation.NESTED)
    fun commit() {
        log.info("===== Sub(NESTED) BEGIN =====")
        log.info(">> Sub 로직 진행...")
        log.info("===== Sub(NESTED) COMMIT =====")
    }

    @Transactional(propagation = Propagation.NESTED)
    fun rollback() {
        log.info("===== Sub(NESTED) BEGIN =====")
        log.info(">> Sub 로직 진행...")
        log.info("===== Sub(NESTED) ROLLBACK =====")

        throw TxException()
    }
}
