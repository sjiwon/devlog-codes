package com.sjiwon.springtransaction._07_nested

import com.sjiwon.springtransaction.TxException
import com.sjiwon.springtransaction.logger
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class NestedMainComponent(
    private val sub: NestedSubComponent,
) {
    private val log: Logger = logger()

    fun case1() {
        log.info("===== Main(No Tx) BEGIN =====")
        log.info(">> Main 로직 진행...")
        sub.commit()
        log.info("===== Main(No Tx) COMMIT =====")
    }

    @Transactional
    fun case2(jpaTx: Boolean) {
        log.info("===== Main(REQUIRED) BEGIN =====")
        log.info(">> Main 로직 진행...")

        if (jpaTx) { // JPA
            sub.rollback()
        } else { // JDBC
            try {
                sub.rollback()
            } catch (_: Exception) {
            } finally {
                log.info("===== Main(REQUIRED) COMMIT =====")
            }
        }
    }

    @Transactional
    fun case3() {
        log.info("===== Main(REQUIRED) BEGIN =====")
        log.info(">> Main 로직 진행...")
        sub.commit()
        log.info("===== Main(REQUIRED) ROLLBACK =====")

        throw TxException()
    }
}
