package com.sjiwon.logging.entrypoint.application

import com.sjiwon.logging.entrypoint.domain.Book
import com.sjiwon.logging.entrypoint.domain.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
) {
    fun save(name: String): Book {
        return bookRepository.save(Book(name = name))
    }
}
