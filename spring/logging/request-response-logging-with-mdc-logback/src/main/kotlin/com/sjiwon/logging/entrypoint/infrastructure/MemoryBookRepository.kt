package com.sjiwon.logging.entrypoint.infrastructure

import com.sjiwon.logging.entrypoint.domain.Book
import com.sjiwon.logging.entrypoint.domain.BookRepository
import org.springframework.stereotype.Repository
import java.lang.reflect.Field
import java.util.concurrent.ConcurrentHashMap

@Repository
class MemoryBookRepository : BookRepository {
    private val datasource: MutableMap<Long, Book> = ConcurrentHashMap()

    override fun save(book: Book): Book {
        val idField: Field = book::class.java.getDeclaredField("id").apply {
            isAccessible = true
        }
        val idValue: Long = book.id
        if (idValue == 0L) {
            val identifier: Long = datasource.size.toLong() + 1
            idField.set(book, identifier)
        }
        datasource[book.id] = book
        return datasource[book.id]!!
    }
}
