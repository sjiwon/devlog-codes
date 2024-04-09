package com.sjiwon.aspect.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "member")
class Member(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L,

    var name: String,
) {
    fun update(name: String) {
        this.name = name
    }
}
