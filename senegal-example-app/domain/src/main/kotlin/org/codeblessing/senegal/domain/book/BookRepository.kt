package org.codeblessing.senegal.domain.book

import org.codeblessing.senegal.domain.author.AuthorId


interface BookRepository {
    fun fetchBookById(bookId: org.codeblessing.senegal.domain.book.BookId): org.codeblessing.senegal.domain.book.Book
    fun fetchAllBooks(): List<org.codeblessing.senegal.domain.book.Book>

    fun fetchAuthorDescriptionById(authorId: org.codeblessing.senegal.domain.author.AuthorId): org.codeblessing.senegal.domain.book.AuthorDescription

    fun fetchAllBooksByAuthor(authorId: org.codeblessing.senegal.domain.author.AuthorId): List<org.codeblessing.senegal.domain.book.Book>

    fun insertBook(book: org.codeblessing.senegal.domain.book.Book)
    fun updateBook(book: org.codeblessing.senegal.domain.book.Book)
    fun deleteBook(book: org.codeblessing.senegal.domain.book.Book)
}
