package com.sjiwon.tx._01_required

import com.sjiwon.tx.TxException
import com.sjiwon.tx.logger
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class RequiredMainComponent(
    private val sub: RequiredSubComponent,
) {
    private val log: Logger = logger()

    @Transactional
    fun case1() {
        log.info("===== Main(REQUIRED) BEGIN =====")
        log.info(">> Main 로직 진행...")
        sub.commit()
        log.info("===== Main(REQUIRED) COMMIT =====")
    }

    @Transactional
    fun case2() {
        log.info("===== Main(REQUIRED) BEGIN =====")
        log.info(">> Main 로직 진행...")
        try {
            sub.rollback()
        } catch (_: Exception) {
            log.info(">> 논리 트랜잭션 Exception 발생...")
        } finally {
            log.info("===== Main(REQUIRED) COMMIT =====")
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
