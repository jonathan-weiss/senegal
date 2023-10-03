package org.codeblessing.senegal.frontendapi.controller.book

import org.codeblessing.senegal.frontendapi.API
import org.codeblessing.senegal.frontendapi.controller.ResponseStatusExceptionFactory
import org.codeblessing.senegal.frontendapi.controller.commons.UuidTO
import org.codeblessing.senegal.frontendapi.facade.BookFacade
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("${org.codeblessing.senegal.frontendapi.API}/books")
class BookController(
    private val bookFacade: org.codeblessing.senegal.frontendapi.facade.BookFacade
) {

    @GetMapping("/entry/{bookId}")
    fun getBook(@PathVariable("bookId") bookId: UUID): org.codeblessing.senegal.frontendapi.controller.book.BookTO {
        try {
            return bookFacade
                .getBookById(org.codeblessing.senegal.frontendapi.controller.commons.UuidTO(bookId))
        } catch (e: IllegalArgumentException) {
            throw org.codeblessing.senegal.frontendapi.controller.ResponseStatusExceptionFactory.createValidationMessageResponseException(e.message ?: "Unknown error.")
        }
    }

    @GetMapping("/all")
    fun getBooks(): List<org.codeblessing.senegal.frontendapi.controller.book.BookTO> {
        try {
            return bookFacade.getAllBooks()
        } catch (e: IllegalArgumentException) {
            throw org.codeblessing.senegal.frontendapi.controller.ResponseStatusExceptionFactory.createValidationMessageResponseException(e.message ?: "Unknown error.")
        }
    }

    @PostMapping("/search")
    fun searchBooks(@RequestBody searchParams: org.codeblessing.senegal.frontendapi.controller.book.SearchBookInstructionTO): List<org.codeblessing.senegal.frontendapi.controller.book.BookTO> {
        try {
            return bookFacade.searchAllBook(searchParams)
        } catch (e: IllegalArgumentException) {
            throw org.codeblessing.senegal.frontendapi.controller.ResponseStatusExceptionFactory.createValidationMessageResponseException(e.message ?: "Unknown error.")
        }
    }


    @GetMapping("/all-by-author/{authorId}")
    fun getBooksByAuthor(@PathVariable("authorId") authorId: UUID): List<org.codeblessing.senegal.frontendapi.controller.book.BookTO> {
        try {
            return bookFacade.getAllBooksByAuthor(
                org.codeblessing.senegal.frontendapi.controller.commons.UuidTO(
                    authorId
                )
            )
        } catch (e: IllegalArgumentException) {
            throw org.codeblessing.senegal.frontendapi.controller.ResponseStatusExceptionFactory.createValidationMessageResponseException(e.message ?: "Unknown error.")
        }
    }

    @PostMapping("/entry")
    fun createBook(@RequestBody request: org.codeblessing.senegal.frontendapi.controller.book.CreateBookInstructionTO): org.codeblessing.senegal.frontendapi.controller.book.BookTO {
        try {
            return bookFacade.createBook(request)
        } catch (e: IllegalArgumentException) {
            throw org.codeblessing.senegal.frontendapi.controller.ResponseStatusExceptionFactory.createValidationMessageResponseException(e.message ?: "Unknown error.")
        }
    }

    @PutMapping("/entry")
    fun updateBook(@RequestBody request: org.codeblessing.senegal.frontendapi.controller.book.UpdateBookInstructionTO): org.codeblessing.senegal.frontendapi.controller.book.BookTO {
        try {
            return bookFacade.updateBook(request)
        } catch (e: IllegalArgumentException) {
            throw org.codeblessing.senegal.frontendapi.controller.ResponseStatusExceptionFactory.createValidationMessageResponseException(e.message ?: "Unknown error.")
        }
    }

    // @DeleteMapping does not support request body
    @PostMapping("/entry/delete")
    fun deleteBook(@RequestBody request: org.codeblessing.senegal.frontendapi.controller.book.DeleteBookInstructionTO) {
        try {
            bookFacade.deleteBook(request)
        } catch (e: IllegalArgumentException) {
            throw org.codeblessing.senegal.frontendapi.controller.ResponseStatusExceptionFactory.createValidationMessageResponseException(e.message ?: "Unknown error.")
        }
    }
}
