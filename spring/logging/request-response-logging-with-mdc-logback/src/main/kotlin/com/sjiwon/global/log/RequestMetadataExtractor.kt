package com.sjiwon.global.log

import jakarta.servlet.http.HttpServletRequest

object RequestMetadataExtractor {
    fun getClientIP(request: HttpServletRequest): String {
        var ip: String? = request.getHeader("X-Forwarded-For")

        if (cannnotDetectIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (cannnotDetectIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (cannnotDetectIp(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP")
        }
        if (cannnotDetectIp(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR")
        }
        if (cannnotDetectIp(ip)) {
            ip = request.remoteAddr
        }

        return ip ?: "Unknown"
    }

    private fun cannnotDetectIp(ip: String?): Boolean {
        return ip.isNullOrEmpty() || "unknown".equals(ip, ignoreCase = true)
    }

    fun getHttpMethod(request: HttpServletRequest): String = request.method

    fun getRequestUriWithQueryString(request: HttpServletRequest): String {
        val requestURI: String = request.requestURI
        val queryString: String? = request.queryString

        if (queryString.isNullOrEmpty()) {
            return requestURI
        }
        return "$requestURI?$queryString"
    }

    fun getSeveralParamsViaParsing(request: HttpServletRequest): String {
        return request.parameterNames
            .toList()
            .joinToString(
                separator = ", ",
                prefix = "[",
                postfix = "]",
            ) { "$it = ${request.getParameter(it)}" }
    }
}
