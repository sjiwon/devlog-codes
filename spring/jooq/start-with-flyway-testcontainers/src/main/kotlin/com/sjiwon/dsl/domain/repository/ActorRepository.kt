package com.sjiwon.dsl.domain.repository

import com.sjiwon.dsl.domain.Actor
import org.springframework.data.jpa.repository.JpaRepository

interface ActorRepository : JpaRepository<Actor, Long>
