package com.sjiwon.dsl.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "film")
class Film(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L,
    val title: String,
    val description: String,
    val release: Int,
    val length: Int,
)
