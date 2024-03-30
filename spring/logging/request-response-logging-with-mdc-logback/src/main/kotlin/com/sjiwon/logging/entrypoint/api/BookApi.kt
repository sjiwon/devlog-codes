package com.sjiwon.logging.entrypoint.api

import com.sjiwon.logging.entrypoint.application.BookService
import com.sjiwon.logging.entrypoint.domain.Book
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookApi(
    private val bookService: BookService,
) {
    data class Request(
        val name: String,
    )

    @PostMapping
    fun save(
        @RequestBody request: Request,
    ): Book = bookService.save(request.name)
}
