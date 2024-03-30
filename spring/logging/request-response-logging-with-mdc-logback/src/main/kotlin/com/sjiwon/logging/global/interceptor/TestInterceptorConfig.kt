package com.sjiwon.logging.global.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class TestInterceptorConfig(
    private val objectMapper: ObjectMapper,
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
//        registry.addInterceptor(QueryStringInterceptor())
//        registry.addInterceptor(FormDataInterceptor())
//        registry.addInterceptor(HttpRequestInterceptor(objectMapper))
//        registry.addInterceptor(HttpResponseInterceptor(objectMapper))
//        registry.addInterceptor(HttpCachingInterceptorV1(objectMapper))
//        registry.addInterceptor(HttpCachingInterceptorV2(objectMapper))
    }
}
