package ch.senegal.example.domain.book

import ch.senegal.example.domain.author.AuthorId


interface BookRepository {
    fun fetchBookById(bookId: BookId): Book
    fun fetchAllBooks(): List<Book>

    fun fetchAllBooksByAuthor(authorId: AuthorId): List<Book>

    fun insertBook(book: Book)
    fun updateBook(book: Book)
    fun deleteBook(book: Book)
}
