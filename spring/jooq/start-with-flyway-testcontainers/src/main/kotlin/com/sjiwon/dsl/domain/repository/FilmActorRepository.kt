package com.sjiwon.dsl.domain.repository

import com.sjiwon.dsl.domain.FilmActor
import org.springframework.data.jpa.repository.JpaRepository

interface FilmActorRepository : JpaRepository<FilmActor, Long>
