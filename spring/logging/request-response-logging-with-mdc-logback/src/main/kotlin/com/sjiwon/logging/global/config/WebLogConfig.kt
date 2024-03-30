package com.sjiwon.logging.global.config

import com.sjiwon.logging.global.filter.MdcLoggingFilter
import com.sjiwon.logging.global.filter.RequestLoggingFilter
import com.sjiwon.logging.global.filter.RequestResponseCachingFilter
import com.sjiwon.logging.global.log.LoggingStatusManager
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebLogConfig {
    @Bean
    fun firstFilter(): FilterRegistrationBean<MdcLoggingFilter> {
        return FilterRegistrationBean<MdcLoggingFilter>().apply {
            order = 1
            filter = MdcLoggingFilter()
            setName("mdcLoggingFilter")
            addUrlPatterns("/api/*")
        }
    }

    @Bean
    fun secondFilter(): FilterRegistrationBean<RequestResponseCachingFilter> {
        return FilterRegistrationBean<RequestResponseCachingFilter>().apply {
            order = 2
            filter = RequestResponseCachingFilter()
            setName("requestResponseCachingFilter")
            addUrlPatterns("/api/*")
        }
    }

    @Bean
    fun thirdFilter(loggingStatusManager: LoggingStatusManager): FilterRegistrationBean<RequestLoggingFilter> {
        return FilterRegistrationBean<RequestLoggingFilter>().apply {
            order = 3
            filter = RequestLoggingFilter(loggingStatusManager, *ignoredUrl)
            setName("requestLoggingFilter")
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
