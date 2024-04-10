package com.sjiwon.global.config

import com.sjiwon.global.filter.CachingDataFilter
import com.sjiwon.global.filter.CachingMdcFilter
import com.sjiwon.global.filter.LoggingDataFilter
import com.sjiwon.global.log.LoggingStatusManager
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebLogConfig {
    @Bean
    fun firstFilter(): FilterRegistrationBean<CachingMdcFilter> {
        return FilterRegistrationBean<CachingMdcFilter>().apply {
            order = 1
            filter = CachingMdcFilter()
            setName("cachingMdcFilter")
            addUrlPatterns("/api/*")
        }
    }

    @Bean
    fun secondFilter(): FilterRegistrationBean<CachingDataFilter> {
        return FilterRegistrationBean<CachingDataFilter>().apply {
            order = 2
            filter = CachingDataFilter()
            setName("cachingDataFilter")
            addUrlPatterns("/api/*")
        }
    }

    @Bean
    fun thirdFilter(loggingStatusManager: LoggingStatusManager): FilterRegistrationBean<LoggingDataFilter> {
        return FilterRegistrationBean<LoggingDataFilter>().apply {
            order = 3
            filter = LoggingDataFilter(loggingStatusManager, *ignoredUrl)
            setName("loggingDataFilter")
            addUrlPatterns("/api/*")
        }
    }

    companion object {
        private val ignoredUrl: Array<String> = arrayOf(
            "/favicon.ico",
            "/error*",
        )
    }
}
