package com.sjiwon.dsl.domain.repository.query.response

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDate

data class ParticipatedFilm @QueryProjection constructor(
    val id: Long,
    val title: String,
    val releaseDate: LocalDate,
    val role: String,
)
