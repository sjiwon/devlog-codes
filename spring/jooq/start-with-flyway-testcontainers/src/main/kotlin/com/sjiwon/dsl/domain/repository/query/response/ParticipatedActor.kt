package com.sjiwon.dsl.domain.repository.query.response

import com.querydsl.core.annotations.QueryProjection

data class ParticipatedActor @QueryProjection constructor(
    val id: Long,
    val name: String,
    val role: String,
)
