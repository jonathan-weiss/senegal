package org.codeblessing.senegal.domain.book

import org.codeblessing.senegal.domain.author.Author
import org.codeblessing.senegal.domain.author.AuthorId
import org.codeblessing.senegal.domain.author.AuthorRepository
import org.codeblessing.senegal.sharedservice.tx.Transactional
import org.springframework.stereotype.Service

@Service
class BookService(
    private val repository: org.codeblessing.senegal.domain.book.BookRepository,
) {

    fun getBook(bookId: org.codeblessing.senegal.domain.book.BookId): org.codeblessing.senegal.domain.book.Book {
        return repository.fetchBookById(bookId)
    }


    @Transactional
    fun createBook(instruction: org.codeblessing.senegal.domain.book.CreateBookInstruction): org.codeblessing.senegal.domain.book.Book {
        val author: org.codeblessing.senegal.domain.book.AuthorDescription = repository.fetchAuthorDescriptionById(instruction.mainAuthorId)
        val book = org.codeblessing.senegal.domain.book.Book.create(instruction, author)
        repository.insertBook(book)
        return getBook(book.bookId)
    }

    @Transactional
    fun updateBook(instruction: org.codeblessing.senegal.domain.book.UpdateBookInstruction): org.codeblessing.senegal.domain.book.Book {
        val existingEntry = repository.fetchBookById(instruction.bookId)
        val author: org.codeblessing.senegal.domain.book.AuthorDescription = repository.fetchAuthorDescriptionById(instruction.mainAuthorId)
        existingEntry.update(instruction, author)
        repository.updateBook(existingEntry)
        return getBook(instruction.bookId)
    }

    @Transactional
    fun deleteBook(instruction: org.codeblessing.senegal.domain.book.DeleteBookInstruction) {
        val existingEntry = repository.fetchBookById(instruction.bookId)
        repository.deleteBook(existingEntry)
    }

    fun getBooks(): List<org.codeblessing.senegal.domain.book.Book> {
        return repository.fetchAllBooks()
    }

    fun getBooksByAuthor(authorId: org.codeblessing.senegal.domain.author.AuthorId): List<org.codeblessing.senegal.domain.book.Book> {
        return repository.fetchAllBooksByAuthor(authorId)
    }

    fun searchAllBook(searchParams: org.codeblessing.senegal.domain.book.SearchBookInstruction): List<org.codeblessing.senegal.domain.book.Book> {
        // TODO implement as SQL search
        return repository.fetchAllBooks().filter { searchParams.mainAuthorId == null || searchParams.mainAuthorId == it.mainAuthor.authorId }
    }


}
