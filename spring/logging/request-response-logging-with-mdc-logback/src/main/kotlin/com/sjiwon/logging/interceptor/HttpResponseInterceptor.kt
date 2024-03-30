package com.sjiwon.logging.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.sjiwon.logger
import com.sjiwon.logging.TestController
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.springframework.web.servlet.HandlerInterceptor

class HttpResponseInterceptor(
    private val objectMapper: ObjectMapper,
) : HandlerInterceptor {
    private val log: Logger = logger()

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?,
    ) {
        val data: TestController.Data = objectMapper.readValue(request.inputStream)
        log.info("[Interceptor] ID=${data.id}, Name=${data.name}")
    }
}
