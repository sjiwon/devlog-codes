package com.sjiwon.tx._02_requires_new

import com.sjiwon.tx.logger
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class RequiresNewMainComponent(
    private val subA: RequiresNewSubComponentA,
    private val subB: RequiresNewSubComponentB,
) {
    private val log: Logger = logger()

    @Transactional
    fun case1() {
        log.info("===== Main(REQUIRED) BEGIN =====")
        log.info(">> Main 로직 진행...")
        subA.commit()
        try {
            subB.rollback()
        } catch (_: Exception) {
        }
        log.info("===== Main(REQUIRED) COMMIT =====")
    }

    @Transactional
    fun case2() {
        log.info("===== Main(REQUIRED) BEGIN =====")
        log.info(">> Main 로직 진행...")
        try {
            subA.rollback()
        } catch (_: Exception) {
        }
        subB.commit()
        log.info("===== Main(REQUIRED) COMMIT =====")
    }
}
