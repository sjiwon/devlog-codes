package com.sjiwon.logging

import com.fasterxml.jackson.databind.ObjectMapper
import com.sjiwon.logger
import com.sjiwon.logging.global.filter.ReadableRequestWrapper
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.ContentCachingRequestWrapper

@RestController
class TestController(
    private val objectMapper: ObjectMapper,
) {
    private val log: Logger = logger()

    data class Data(
        val id: Long,
        val name: String,
    )

    @GetMapping("/api/v1")
    fun queryString(@ModelAttribute data: Data): Data {
        log.info("[Controller] ID=${data.id}, Name=${data.name}")
        return data
    }

    @PostMapping("/api/v2")
    fun formData(@ModelAttribute data: Data): Data {
        log.info("[Controller] ID=${data.id}, Name=${data.name}")
        return data
    }

    @PostMapping("/api/v3")
    fun request(@RequestBody data: Data): Data {
        log.info("[Controller] ID=${data.id}, Name=${data.name}")
        return data
    }

    @PostMapping("/api/v4")
    fun response(@RequestBody data: Data): Data {
        log.info("[Controller] ID=${data.id}, Name=${data.name}")
        return data
    }

    @PostMapping("/api/caching/v1")
    fun cachingV1(
        @RequestBody data: Data,
        request: HttpServletRequest,
    ): Data {
        log.info("[Controller] Data=$data")

        val wrapper = request as ContentCachingRequestWrapper
        val wrapperData = objectMapper.readTree(wrapper.contentAsByteArray)
        log.info("[Controller - Caching] Data=$wrapperData")

        return data
    }

    @PostMapping("/api/caching/v2")
    fun cachingV2(
        @RequestBody data: Data,
        request: HttpServletRequest,
    ): Data {
        log.info("[Controller] Data=$data")

        val wrapper = request as ReadableRequestWrapper
        val wrapperData = objectMapper.readTree(wrapper.contentAsByteArray)
        log.info("[Controller - Caching] Data=$wrapperData")

        return data
    }
}
