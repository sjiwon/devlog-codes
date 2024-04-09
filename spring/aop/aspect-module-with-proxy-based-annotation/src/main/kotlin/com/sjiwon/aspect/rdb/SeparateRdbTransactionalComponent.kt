package com.sjiwon.aspect.rdb

import com.sjiwon.aspect.domain.Member
import com.sjiwon.aspect.domain.MemberRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SeparateRdbTransactionalComponent(
    private val memberRepository: MemberRepository,
) {
    @Transactional
    fun invoke() {
        memberRepository.saveAll(
            listOf(
                Member(name = "MemberA"),
                Member(name = "MemberB"),
                Member(name = "MemberC"),
            )
        )
        throw RuntimeException()
    }
}
