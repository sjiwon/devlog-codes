package com.sjiwon.concurrency.model

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TicketRepository : JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t WHERE t.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByIdWithLock(@Param("id") id: Long): Ticket?
}
