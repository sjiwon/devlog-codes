package com.sjiwon.logging.book.domain

interface BookRepository {
    fun save(book: Book): Book
}
