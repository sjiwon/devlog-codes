package com.sjiwon.logging.global.filter

import com.sjiwon.logging.global.filter.MdcKey.REQUEST_ID
import com.sjiwon.logging.global.filter.MdcKey.REQUEST_IP
import com.sjiwon.logging.global.filter.MdcKey.REQUEST_METHOD
import com.sjiwon.logging.global.filter.MdcKey.REQUEST_PARAMS
import com.sjiwon.logging.global.filter.MdcKey.REQUEST_TIME
import com.sjiwon.logging.global.filter.MdcKey.REQUEST_URI
import com.sjiwon.logging.global.filter.MdcKey.START_TIME_MILLIS
import com.sjiwon.logging.global.log.RequestMetadataExtractor.getClientIP
import com.sjiwon.logging.global.log.RequestMetadataExtractor.getHttpMethod
import com.sjiwon.logging.global.log.RequestMetadataExtractor.getRequestUriWithQueryString
import com.sjiwon.logging.global.log.RequestMetadataExtractor.getSeveralParamsViaParsing
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.MDC
import java.time.LocalDateTime
import java.util.*

class CachingMdcFilter : Filter {
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain,
    ) {
        setMdc(request as HttpServletRequest)
        chain.doFilter(request, response)
        MDC.clear()
    }

    private fun setMdc(request: HttpServletRequest) {
        MDC.put(REQUEST_ID.name, UUID.randomUUID().toString())
        MDC.put(REQUEST_IP.name, getClientIP(request))
        MDC.put(REQUEST_METHOD.name, getHttpMethod(request))
        MDC.put(REQUEST_URI.name, getRequestUriWithQueryString(request))
        MDC.put(REQUEST_PARAMS.name, getSeveralParamsViaParsing(request))
        MDC.put(REQUEST_TIME.name, LocalDateTime.now().toString())
        MDC.put(START_TIME_MILLIS.name, System.currentTimeMillis().toString())
    }
}
