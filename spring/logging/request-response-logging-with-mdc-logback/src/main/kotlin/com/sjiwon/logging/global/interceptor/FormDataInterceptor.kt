package com.sjiwon.logging.global.interceptor

import com.sjiwon.logger
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.springframework.web.servlet.HandlerInterceptor

class FormDataInterceptor : HandlerInterceptor {
    private val log: Logger = logger()

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        log.info("[Interceptor] ID=${request.getParameter("id")}, Name=${request.getParameter("name")}")
        return true
    }
}
