package com.sjiwon.tx.logic

import com.sjiwon.tx.model.Bucket
import com.sjiwon.tx.model.Member
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.EntityTransaction
import org.springframework.stereotype.Component

@Component
class JpaComponent(
    private val emf: EntityManagerFactory,
) {
    fun logic(exception: Boolean) {
        val em: EntityManager = emf.createEntityManager()
        val tx: EntityTransaction = em.transaction

        try {
            tx.begin()

            // 1. 사용자 정보 저장
            val member = Member(name = "Member")
            em.persist(member)

            // 2. 사용자 개인 사물함 정보 저장
            val bucket = Bucket(memberId = member.id, capacity = 10)
            em.persist(bucket)

            // 예외 발생? All or Nothing?
            if (exception) {
                throw RuntimeException()
            }

            tx.commit()
        } catch (ex: Exception) {
            tx.rollback()
        }
    }
}
