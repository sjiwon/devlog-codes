package com.sjiwon.dsl.domain.repository.query

import com.sjiwon.dsl.domain.repository.query.response.ParticipatedActor
import com.sjiwon.dsl.domain.repository.query.response.ParticipatedFilm

interface QueryRepository {
    /**
     * 특정 배우가 참여한 영화 정보
     */
    fun fetchParticipatedFilms(name: String): List<ParticipatedFilm>

    /**
     * 특정 영화에 참여한 배우 정보
     */
    fun fetchParticipatedActors(title: String): List<ParticipatedActor>
}
