package com.sjiwon.dsl.config.log

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class P6SpyConfig {
    @Bean
    fun p6SpyCustomEventListener(): P6SpyEventListener = P6SpyEventListener()

    @Bean
    fun p6SpyCustomFormatter(): P6SpyFormatter = P6SpyFormatter()
}
