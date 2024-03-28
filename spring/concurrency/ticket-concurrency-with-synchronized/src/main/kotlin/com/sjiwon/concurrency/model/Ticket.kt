package com.sjiwon.concurrency.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "ticket")
class Ticket(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L,

    var stock: Int,
) {
    fun purchase(amount: Int) {
        if (stock == 0) {
            throw RuntimeException("티켓이 매진되었습니다.")
        }
        if (stock < amount) {
            throw RuntimeException("티켓 재고가 부족합니다.")
        }
        stock -= amount
    }
}
