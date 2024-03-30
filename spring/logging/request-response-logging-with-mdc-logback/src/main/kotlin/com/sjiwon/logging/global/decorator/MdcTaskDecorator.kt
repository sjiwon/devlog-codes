package com.sjiwon.logging.global.decorator

import org.slf4j.MDC
import org.springframework.core.task.TaskDecorator

class MdcTaskDecorator : TaskDecorator {
    override fun decorate(runnable: Runnable): Runnable {
        val context = MDC.getCopyOfContextMap()

        return Runnable {
            if (context != null) {
                MDC.setContextMap(context)
            }
            runnable.run()
        }
    }
}
