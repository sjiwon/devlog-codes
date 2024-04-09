package com.sjiwon.aspect.redis

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Aspect
@Component
class ExtractCommonLogicRedisTxAop(
    @Qualifier("nonTxTemplate") private val nonTxTemplate: StringRedisTemplate,
    @Qualifier("txTemplate") private val txTemplate: StringRedisTemplate,
    private val separateNonTxTemplate: SeparateRedisTransactionalComponentA,
    private val separateTxTemplate: SeparateRedisTransactionalComponentB,
) {
    @Transactional
    @Around("@annotation(com.sjiwon.aspect.redis.ExtractCommonLogicRedisTxTypeA)")
    fun typeA(joinPoint: ProceedingJoinPoint): Any {
        println("AOP - ExtractCommonLogicRedisTxTypeA")
        val executor = nonTxTemplate.opsForValue()
        executor.set("typeA-nonTxTemplate-1", "success")
        executor.set("typeA-nonTxTemplate-2", "success")
        executor.set("typeA-nonTxTemplate-3", "success")
        throw RuntimeException()
    }

    @Transactional
    @Around("@annotation(com.sjiwon.aspect.redis.ExtractCommonLogicRedisTxTypeB)")
    fun typeB(joinPoint: ProceedingJoinPoint): Any {
        println("AOP - ExtractCommonLogicRedisTxTypeB")
        val executor = txTemplate.opsForValue()
        executor.set("typeB-nonTxTemplate-1", "success")
        executor.set("typeB-nonTxTemplate-2", "success")
        executor.set("typeB-nonTxTemplate-3", "success")
        return joinPoint.proceed()
    }

    @Around("@annotation(com.sjiwon.aspect.redis.ExtractCommonLogicRedisTxTypeC)")
    fun typeC(joinPoint: ProceedingJoinPoint): Any {
        println("AOP - ExtractCommonLogicRedisTxTypeC")
        separateNonTxTemplate.invoke()
        return joinPoint.proceed()
    }

    @Around("@annotation(com.sjiwon.aspect.redis.ExtractCommonLogicRedisTxTypeD)")
    fun typeD(joinPoint: ProceedingJoinPoint): Any {
        println("AOP - ExtractCommonLogicRedisTxTypeD")
        separateTxTemplate.invoke()
        return joinPoint.proceed()
    }
}
