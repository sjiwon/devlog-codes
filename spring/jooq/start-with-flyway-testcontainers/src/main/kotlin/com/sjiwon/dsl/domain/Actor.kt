package com.sjiwon.dsl.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "actor")
class Actor(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L,
    val name: String,
)
