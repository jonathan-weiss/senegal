package ch.senegal.example.frontendapi.facade

import ch.senegal.example.domain.author.AuthorId
import ch.senegal.example.domain.book.BookId
import ch.senegal.example.frontendapi.controller.commons.UuidTO
import ch.senegal.example.domain.book.BookService
import ch.senegal.example.frontendapi.controller.author.AuthorTO
import ch.senegal.example.frontendapi.controller.author.SearchAuthorInstructionTO
import ch.senegal.example.frontendapi.controller.book.*

import org.springframework.stereotype.Service

@Service
class BookFacade(
    private val bookService: BookService
) {

    fun getBookById(id: UuidTO): BookTO {
        val book = bookService.getBook(BookId(id.uuid))
        return BookTO.fromDomain(book)
    }

    fun createBook(instruction: CreateBookInstructionTO): BookTO {
        val createdBook = bookService.createBook(instruction.toDomain())
        return BookTO.fromDomain(createdBook)
    }

    fun updateBook(instruction: UpdateBookInstructionTO): BookTO {
        val updatedBook = bookService.updateBook(instruction.toDomain())
        return BookTO.fromDomain(updatedBook)
    }

    fun deleteBook(instruction: DeleteBookInstructionTO) {
        bookService.deleteBook(instruction.toDomain())
    }

    fun getAllBooks(): List<BookTO> {
        return bookService.getBooks().map { BookTO.fromDomain(it) }
    }

    fun searchAllBook(searchParams: SearchBookInstructionTO): List<BookTO> {
        return bookService.searchAllBook(searchParams.toDomain()).map { BookTO.fromDomain(it) }
    }


    fun getAllBooksByAuthor(authorId: UuidTO): List<BookTO> {
        return bookService.getBooksByAuthor(AuthorId(authorId.uuid)).map { BookTO.fromDomain(it) }
    }
}
