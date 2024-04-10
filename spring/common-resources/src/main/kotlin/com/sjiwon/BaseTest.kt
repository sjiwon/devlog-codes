package com.sjiwon

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
annotation class IntegrationTest
