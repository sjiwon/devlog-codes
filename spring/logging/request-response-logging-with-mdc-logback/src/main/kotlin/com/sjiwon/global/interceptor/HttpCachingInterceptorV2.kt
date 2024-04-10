package com.sjiwon.global.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import com.sjiwon.global.filter.ReadableRequestWrapper
import com.sjiwon.logger
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.springframework.web.servlet.HandlerInterceptor

class HttpCachingInterceptorV2(
    private val objectMapper: ObjectMapper,
) : HandlerInterceptor {
    private val log: Logger = logger()

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        val wrapper = request as ReadableRequestWrapper
        val wrapperData = objectMapper.readTree(wrapper.contentAsByteArray)
        log.info("[Interceptor] Data=$wrapperData")
        return true
    }
}
