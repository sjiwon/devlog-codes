package com.sjiwon.dsl.jdsl

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import com.sjiwon.dsl.domain.Actor
import com.sjiwon.dsl.domain.Film
import com.sjiwon.dsl.domain.FilmActor
import com.sjiwon.dsl.domain.repository.query.QueryRepository
import com.sjiwon.dsl.domain.repository.query.response.ParticipatedActor
import com.sjiwon.dsl.domain.repository.query.response.ParticipatedFilm
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class JdslRepository(
    private val em: EntityManager,
    private val context: JpqlRenderContext,
) : QueryRepository {
    override fun fetchParticipatedFilms(name: String): List<ParticipatedFilm> {
        val query: SelectQuery<ParticipatedFilm> = jpql {
            selectNew<ParticipatedFilm>(
                path(Film::id),
                path(Film::title),
                path(Film::releaseDate),
                path(FilmActor::role),
            ).from(
                entity(FilmActor::class),
                innerJoin(Actor::class).on(path(Actor::id).equal(path(FilmActor::actorId))),
                innerJoin(Film::class).on(path(Film::id).equal(path(FilmActor::filmId)))
            ).where(
                path(Actor::name).equal(name),
            ).orderBy(
                path(Film::releaseDate).asc(),
            )
        }
        return em.createQuery(query, context).resultList
    }

    override fun fetchParticipatedActors(title: String): List<ParticipatedActor> {
        val query: SelectQuery<ParticipatedActor> = jpql {
            selectNew<ParticipatedActor>(
                path(Actor::id),
                path(Actor::name),
                path(FilmActor::role),
            ).from(
                entity(FilmActor::class),
                innerJoin(Actor::class).on(path(Actor::id).equal(path(FilmActor::actorId))),
                innerJoin(Film::class).on(path(Film::id).equal(path(FilmActor::filmId)))
            ).where(
                path(Film::title).equal(title),
            ).orderBy(
                path(Actor::name).asc(),
            )
        }
        return em.createQuery(query, context).resultList
    }
}
