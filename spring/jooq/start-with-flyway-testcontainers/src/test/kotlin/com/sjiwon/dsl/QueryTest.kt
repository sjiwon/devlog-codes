package com.sjiwon.dsl

import com.sjiwon.IntegrationTest
import com.sjiwon.dsl.common.DatabaseCleanerEachCallbackExtension
import com.sjiwon.dsl.common.MySqlTestContainers
import com.sjiwon.dsl.domain.Actor
import com.sjiwon.dsl.domain.Film
import com.sjiwon.dsl.domain.FilmActor
import com.sjiwon.dsl.domain.repository.ActorRepository
import com.sjiwon.dsl.domain.repository.FilmActorRepository
import com.sjiwon.dsl.domain.repository.FilmRepository
import com.sjiwon.dsl.domain.repository.query.response.ParticipatedActor
import com.sjiwon.dsl.domain.repository.query.response.ParticipatedFilm
import com.sjiwon.dsl.jdsl.JdslRepository
import com.sjiwon.dsl.jooq.JooqRepository
import com.sjiwon.dsl.querydsl.QueryDslRepository
import io.kotest.matchers.collections.shouldContainExactly
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ContextConfiguration
import java.time.LocalDate

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(
    initializers = [MySqlTestContainers.Initializer::class],
)
@ExtendWith(DatabaseCleanerEachCallbackExtension::class)
@IntegrationTest
class QueryTest(
    private val actorRepository: ActorRepository,
    private val filmRepository: FilmRepository,
    private val filmActorRepository: FilmActorRepository,
    private val jooq: JooqRepository,
    private val queryDsl: QueryDslRepository,
    private val jdsl: JdslRepository,
) {
    private lateinit var actors: List<Actor>
    private lateinit var films: List<Film>

    @BeforeEach
    fun setUp() {
        actors = actorRepository.saveAll(
            listOf(Actor(name = "Actor1"), Actor(name = "Actor2"), Actor(name = "Actor3"))
        )
        films = filmRepository.saveAll(
            listOf(
                Film(title = "Film1", description = "Hello Film1", releaseDate = LocalDate.of(2023, 5, 1), length = 80),
                Film(title = "Film2", description = "Hello Film2", releaseDate = LocalDate.of(2018, 2, 10), length = 100),
                Film(title = "Film3", description = "Hello Film3", releaseDate = LocalDate.of(2020, 6, 18), length = 90),
            )
        )
        filmActorRepository.saveAll(
            listOf(
                FilmActor(actorId = actors[0].id, filmId = films[0].id, role = filmRole(films[0], actors[0])),
                FilmActor(actorId = actors[1].id, filmId = films[0].id, role = filmRole(films[0], actors[1])),
                FilmActor(actorId = actors[2].id, filmId = films[0].id, role = filmRole(films[0], actors[2])),

                FilmActor(actorId = actors[0].id, filmId = films[1].id, role = filmRole(films[1], actors[0])),
                FilmActor(actorId = actors[1].id, filmId = films[1].id, role = filmRole(films[1], actors[1])),

                FilmActor(actorId = actors[0].id, filmId = films[2].id, role = filmRole(films[2], actors[0])),
                FilmActor(actorId = actors[2].id, filmId = films[2].id, role = filmRole(films[2], actors[2])),
            )
        )
    }

    @Test
    fun `특정 배우가 참여한 영화 정보를 조회한다`() {
        val resultJooq1 = jooq.fetchParticipatedFilms(actors[0].name)
        val resultQueryDsl1 = queryDsl.fetchParticipatedFilms(actors[0].name)
        val resultJdsl1 = jdsl.fetchParticipatedFilms(actors[0].name)
        assertParticipatedFilmsMatch(
            result = listOf(resultJooq1, resultQueryDsl1, resultJdsl1),
            filmIds = listOf(films[1].id, films[2].id, films[0].id),
            actorRoles = listOf(filmRole(films[1], actors[0]), filmRole(films[2], actors[0]), filmRole(films[0], actors[0])),
        )

        val resultJooq2 = jooq.fetchParticipatedFilms(actors[1].name)
        val resultQueryDsl2 = queryDsl.fetchParticipatedFilms(actors[1].name)
        val resultJdsl2 = jdsl.fetchParticipatedFilms(actors[1].name)
        assertParticipatedFilmsMatch(
            result = listOf(resultJooq2, resultQueryDsl2, resultJdsl2),
            filmIds = listOf(films[1].id, films[0].id),
            actorRoles = listOf(filmRole(films[1], actors[1]), filmRole(films[0], actors[1])),
        )

        val resultJooq3 = jooq.fetchParticipatedFilms(actors[2].name)
        val resultQueryDsl3 = queryDsl.fetchParticipatedFilms(actors[2].name)
        val resultJdsl3 = jdsl.fetchParticipatedFilms(actors[2].name)
        assertParticipatedFilmsMatch(
            result = listOf(resultJooq3, resultQueryDsl3, resultJdsl3),
            filmIds = listOf(films[2].id, films[0].id),
            actorRoles = listOf(filmRole(films[2], actors[2]), filmRole(films[0], actors[2])),
        )
    }

    @Test
    fun `특정 영화에 참여한 배우 정보를 조회한다`() {
        val resultJooq1 = jooq.fetchParticipatedActors(films[0].title)
        val resultQueryDsl1 = queryDsl.fetchParticipatedActors(films[0].title)
        val resultJdsl1 = jdsl.fetchParticipatedActors(films[0].title)
        assertParticipatedActorsMatch(
            result = listOf(resultJooq1, resultQueryDsl1, resultJdsl1),
            actorIds = listOf(actors[0].id, actors[1].id, actors[2].id),
            actorRoles = listOf(filmRole(films[0], actors[0]), filmRole(films[0], actors[1]), filmRole(films[0], actors[2])),
        )

        val resultJooq2 = jooq.fetchParticipatedActors(films[1].title)
        val resultQueryDsl2 = queryDsl.fetchParticipatedActors(films[1].title)
        val resultJdsl2 = jdsl.fetchParticipatedActors(films[1].title)
        assertParticipatedActorsMatch(
            result = listOf(resultJooq2, resultQueryDsl2, resultJdsl2),
            actorIds = listOf(actors[0].id, actors[1].id),
            actorRoles = listOf(filmRole(films[1], actors[0]), filmRole(films[1], actors[1])),
        )

        val resultJooq3 = jooq.fetchParticipatedActors(films[2].title)
        val resultQueryDsl3 = queryDsl.fetchParticipatedActors(films[2].title)
        val resultJdsl3 = jdsl.fetchParticipatedActors(films[2].title)
        assertParticipatedActorsMatch(
            result = listOf(resultJooq3, resultQueryDsl3, resultJdsl3),
            actorIds = listOf(actors[0].id, actors[2].id),
            actorRoles = listOf(filmRole(films[2], actors[0]), filmRole(films[2], actors[2])),
        )
    }

    private fun filmRole(
        film: Film,
        actor: Actor,
    ): String = "File${film.id}-Role${actor.id}"

    private fun assertParticipatedFilmsMatch(
        result: List<List<ParticipatedFilm>>,
        filmIds: List<Long>,
        actorRoles: List<String>,
    ) {
        result.forEach { record ->
            record.map { it.id } shouldContainExactly filmIds
            record.map { it.role } shouldContainExactly actorRoles
        }
    }

    private fun assertParticipatedActorsMatch(
        result: List<List<ParticipatedActor>>,
        actorIds: List<Long>,
        actorRoles: List<String>,
    ) {
        result.forEach { record ->
            record.map { it.id } shouldContainExactly actorIds
            record.map { it.role } shouldContainExactly actorRoles
        }
    }
}
