package com.sjiwon.logging.global.log

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAop(
    private val loggingTracer: LoggingTracer,
) {
    @Pointcut("execution(* com.sjiwon.logging..*(..))")
    private fun includeComponent() {
    }

    @Pointcut(
        """
            !execution(* com.sjiwon.logging.global.config..*(..))
            && !execution(* com.sjiwon.logging.global.decorator..*(..))
            && !execution(* com.sjiwon.logging.global.filter..*(..))
            && !execution(* com.sjiwon.logging.global.log..*(..))
        """,
    )
    private fun excludeComponent() {
    }

    @Around("includeComponent() && excludeComponent()")
    fun doLogging(joinPoint: ProceedingJoinPoint): Any? {
        val methodSignature: String = joinPoint.signature.toShortString()
        val args: Array<Any?> = joinPoint.args
        loggingTracer.methodCall(methodSignature, args)
        try {
            val result: Any? = joinPoint.proceed()
            loggingTracer.methodReturn(methodSignature)
            return result
        } catch (e: Throwable) {
            loggingTracer.throwException(methodSignature, e)
            throw e
        }
    }
}
