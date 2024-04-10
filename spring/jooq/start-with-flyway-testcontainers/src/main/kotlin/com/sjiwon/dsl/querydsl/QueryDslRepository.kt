package com.sjiwon.dsl.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sjiwon.dsl.domain.QActor.actor
import com.sjiwon.dsl.domain.QFilm.film
import com.sjiwon.dsl.domain.QFilmActor.filmActor
import com.sjiwon.dsl.domain.repository.query.QueryRepository
import com.sjiwon.dsl.domain.repository.query.response.ParticipatedActor
import com.sjiwon.dsl.domain.repository.query.response.ParticipatedFilm
import com.sjiwon.dsl.domain.repository.query.response.QParticipatedActor
import com.sjiwon.dsl.domain.repository.query.response.QParticipatedFilm
import org.springframework.stereotype.Repository

@Repository
class QueryDslRepository(
    private val query: JPAQueryFactory,
) : QueryRepository {
    override fun fetchParticipatedFilms(name: String): List<ParticipatedFilm> {
        return query.select(
            QParticipatedFilm(
                film.id,
                film.title,
                film.releaseDate,
                filmActor.role
            )
        ).from(filmActor)
            .innerJoin(actor).on(actor.id.eq(filmActor.actorId))
            .innerJoin(film).on(film.id.eq(filmActor.filmId))
            .where(actor.name.eq(name))
            .orderBy(film.releaseDate.asc())
            .fetch()
    }

    override fun fetchParticipatedActors(title: String): List<ParticipatedActor> {
        return query.select(
            QParticipatedActor(
                actor.id,
                actor.name,
                filmActor.role
            )
        ).from(filmActor)
            .innerJoin(actor).on(actor.id.eq(filmActor.actorId))
            .innerJoin(film).on(film.id.eq(filmActor.filmId))
            .where(film.title.eq(title))
            .orderBy(actor.name.asc())
            .fetch()
    }
}
