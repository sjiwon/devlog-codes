package com.sjiwon.dsl.domain.repository

import com.sjiwon.dsl.domain.Film
import org.springframework.data.jpa.repository.JpaRepository

interface FilmRepository : JpaRepository<Film, Long>
