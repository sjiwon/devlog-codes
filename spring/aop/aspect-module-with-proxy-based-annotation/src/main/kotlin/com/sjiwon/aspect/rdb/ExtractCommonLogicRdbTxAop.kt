package com.sjiwon.aspect.rdb

import com.sjiwon.aspect.domain.Member
import com.sjiwon.aspect.domain.MemberRepository
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionTemplate

@Aspect
@Component
class ExtractCommonLogicRdbTxAop(
    private val memberRepository: MemberRepository,
    private val separateRdbTransactionalComponent: SeparateRdbTransactionalComponent,
    private val transactionTemplate: TransactionTemplate,
) {
    @Transactional
    @Around("@annotation(com.sjiwon.aspect.rdb.ExtractCommonLogicRdbTxTypeA)")
    fun typeA(joinPoint: ProceedingJoinPoint): Any {
        println("AOP - ExtractCommonLogicRdbTxTypeA")
        memberRepository.saveAll(
            listOf(
                Member(name = "MemberA"),
                Member(name = "MemberB"),
                Member(name = "MemberC"),
            )
        )
        throw RuntimeException()
    }

    @Around("@annotation(com.sjiwon.aspect.rdb.ExtractCommonLogicRdbTxTypeB)")
    fun typeB(joinPoint: ProceedingJoinPoint): Any {
        println("AOP - ExtractCommonLogicRdbTxTypeB")
        separateRdbTransactionalComponent.invoke()
        return joinPoint.proceed()
    }

    @Around("@annotation(com.sjiwon.aspect.rdb.ExtractCommonLogicRdbTxTypeC)")
    fun typeC(joinPoint: ProceedingJoinPoint): Any {
        println("AOP - ExtractCommonLogicRdbTxTypeC")
        transactionTemplate.executeWithoutResult {
            memberRepository.saveAll(
                listOf(
                    Member(name = "MemberA"),
                    Member(name = "MemberB"),
                    Member(name = "MemberC"),
                )
            )
            throw RuntimeException()
        }
        return joinPoint.proceed()
    }
}
