package com.sjiwon.book.domain

interface BookRepository {
    fun save(book: Book): Book
}
