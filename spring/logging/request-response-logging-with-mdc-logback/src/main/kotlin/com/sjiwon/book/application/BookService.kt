package com.sjiwon.book.application

import com.sjiwon.book.domain.Book
import com.sjiwon.book.domain.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
) {
    fun save(name: String): Book {
        return bookRepository.save(Book(name = name))
    }
}
