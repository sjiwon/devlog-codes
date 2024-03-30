package com.sjiwon.logging.global.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import com.sjiwon.logger
import com.sjiwon.logging.global.filter.ReadableRequestWrapper
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
