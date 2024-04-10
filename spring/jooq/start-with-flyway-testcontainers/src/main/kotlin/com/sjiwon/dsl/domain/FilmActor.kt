package com.sjiwon.dsl.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "film_actor")
class FilmActor(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L,
    val actorId: Long,
    val filmId: Long,
    val role: String,
)
