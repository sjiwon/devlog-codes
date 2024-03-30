package com.sjiwon.logging.entrypoint.domain

interface BookRepository {
    fun save(book: Book): Book
}
