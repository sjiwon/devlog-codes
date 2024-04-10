package com.sjiwon.dsl.jooq

import com.sjiwon.dsl.domain.repository.query.QueryRepository
import com.sjiwon.dsl.domain.repository.query.response.ParticipatedActor
import com.sjiwon.dsl.domain.repository.query.response.ParticipatedFilm
import com.sjiwon.jooq.tables.JActor.ACTOR
import com.sjiwon.jooq.tables.JFilm.FILM
import com.sjiwon.jooq.tables.JFilmActor.FILM_ACTOR
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class JooqRepository(
    private val dsl: DSLContext,
) : QueryRepository {
    override fun fetchParticipatedFilms(name: String): List<ParticipatedFilm> {
        return dsl.select(
            FILM.ID,
            FILM.TITLE,
            FILM.RELEASE_DATE,
            FILM_ACTOR.ROLE,
        ).from(FILM_ACTOR)
            .innerJoin(ACTOR).on(ACTOR.ID.eq(FILM_ACTOR.ACTOR_ID))
            .innerJoin(FILM).on(FILM.ID.eq(FILM_ACTOR.FILM_ID))
            .where(ACTOR.NAME.eq(name))
            .orderBy(FILM.RELEASE_DATE.asc())
            .fetchInto(ParticipatedFilm::class.java)
    }

    override fun fetchParticipatedActors(title: String): List<ParticipatedActor> {
        return dsl.select(
            ACTOR.ID,
            ACTOR.NAME,
            FILM_ACTOR.ROLE,
        ).from(FILM_ACTOR)
            .innerJoin(ACTOR).on(ACTOR.ID.eq(FILM_ACTOR.ACTOR_ID))
            .innerJoin(FILM).on(FILM.ID.eq(FILM_ACTOR.FILM_ID))
            .where(FILM.TITLE.eq(title))
            .orderBy(ACTOR.NAME.asc())
            .fetchInto(ParticipatedActor::class.java)
    }
}
