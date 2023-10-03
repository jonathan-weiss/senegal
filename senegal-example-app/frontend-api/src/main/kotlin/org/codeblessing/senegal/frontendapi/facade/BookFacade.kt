package org.codeblessing.senegal.frontendapi.facade

import org.springframework.stereotype.Service

@Service
class BookFacade(
    private val bookService: org.codeblessing.senegal.domain.book.BookService
) {

    fun getBookById(id: org.codeblessing.senegal.frontendapi.controller.commons.UuidTO): org.codeblessing.senegal.frontendapi.controller.book.BookTO {
        val book = bookService.getBook(org.codeblessing.senegal.domain.book.BookId(id.uuid))
        return org.codeblessing.senegal.frontendapi.controller.book.BookTO.fromDomain(book)
    }

    fun createBook(instruction: org.codeblessing.senegal.frontendapi.controller.book.CreateBookInstructionTO): org.codeblessing.senegal.frontendapi.controller.book.BookTO {
        val createdBook = bookService.createBook(instruction.toDomain())
        return org.codeblessing.senegal.frontendapi.controller.book.BookTO.fromDomain(createdBook)
    }

    fun updateBook(instruction: org.codeblessing.senegal.frontendapi.controller.book.UpdateBookInstructionTO): org.codeblessing.senegal.frontendapi.controller.book.BookTO {
        val updatedBook = bookService.updateBook(instruction.toDomain())
        return org.codeblessing.senegal.frontendapi.controller.book.BookTO.fromDomain(updatedBook)
    }

    fun deleteBook(instruction: org.codeblessing.senegal.frontendapi.controller.book.DeleteBookInstructionTO) {
        bookService.deleteBook(instruction.toDomain())
    }

    fun getAllBooks(): List<org.codeblessing.senegal.frontendapi.controller.book.BookTO> {
        return bookService.getBooks().map { org.codeblessing.senegal.frontendapi.controller.book.BookTO.fromDomain(it) }
    }

    fun searchAllBook(searchParams: org.codeblessing.senegal.frontendapi.controller.book.SearchBookInstructionTO): List<org.codeblessing.senegal.frontendapi.controller.book.BookTO> {
        return bookService.searchAllBook(searchParams.toDomain()).map { org.codeblessing.senegal.frontendapi.controller.book.BookTO.fromDomain(it) }
    }


    fun getAllBooksByAuthor(authorId: org.codeblessing.senegal.frontendapi.controller.commons.UuidTO): List<org.codeblessing.senegal.frontendapi.controller.book.BookTO> {
        return bookService.getBooksByAuthor(org.codeblessing.senegal.domain.author.AuthorId(authorId.uuid)).map { org.codeblessing.senegal.frontendapi.controller.book.BookTO.fromDomain(it) }
    }
}
