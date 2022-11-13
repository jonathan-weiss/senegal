package ch.senegal.example.service.book

import ch.senegal.example.domain.book.Book
import ch.senegal.example.shareddomain.BookId

interface BookRepository {
    fun fetchBookById(bookId: BookId): Book
    fun fetchAllBooks(): List<Book>

    fun insertBook(book: Book)
    fun updateBook(book: Book)
    fun deleteBook(book: Book)
}
