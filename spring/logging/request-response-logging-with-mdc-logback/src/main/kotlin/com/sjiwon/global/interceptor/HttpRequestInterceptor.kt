package com.sjiwon.global.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.sjiwon.TestController
import com.sjiwon.logger
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.springframework.web.servlet.HandlerInterceptor

class HttpRequestInterceptor(
    private val objectMapper: ObjectMapper,
) : HandlerInterceptor {
    private val log: Logger = logger()

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        val data: TestController.Data = objectMapper.readValue(request.inputStream)
        log.info("[Interceptor] ID=${data.id}, Name=${data.name}")
        return true
    }
}
