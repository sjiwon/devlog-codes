package com.sjiwon.book.api

import com.sjiwon.book.application.BookService
import com.sjiwon.book.domain.Book
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

    @PostMapping("/api/v1/books")
    fun save(
        @RequestBody request: Request,
    ): Book = bookService.save(request.name)
}
