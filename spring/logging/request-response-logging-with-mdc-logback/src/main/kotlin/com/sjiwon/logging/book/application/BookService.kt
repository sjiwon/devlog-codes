package com.sjiwon.logging.book.application

import com.sjiwon.logging.book.domain.Book
import com.sjiwon.logging.book.domain.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
) {
    fun save(name: String): Book {
        return bookRepository.save(Book(name = name))
    }
}
