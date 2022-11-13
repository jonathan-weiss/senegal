package ch.senegal.example.frontendapi.controller.book

import ch.senegal.example.frontendapi.API
import ch.senegal.example.frontendapi.controller.ResponseStatusExceptionFactory
import ch.senegal.example.frontendapi.controller.commons.UuidTO
import ch.senegal.example.frontendapi.facade.BookFacade
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("$API/books")
@Tag(name = "BookApi")
class BookController(
    private val bookFacade: BookFacade
) {

    @GetMapping("/entry/{bookId}")
    fun getBook(@PathVariable("bookId") bookId: UUID): BookTO {
        try {
            return bookFacade
                .getBookById(UuidTO(bookId))
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusExceptionFactory.createValidationMessageResponseException(e.message ?: "Unknown error.")
        }
    }

    @GetMapping("/all")
    fun getBooks(): List<BookTO> {
        try {
            return bookFacade.getAllBooks()
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusExceptionFactory.createValidationMessageResponseException(e.message ?: "Unknown error.")
        }
    }

    @PostMapping("/entry")
    fun createBook(@RequestBody request: CreateBookInstructionTO): BookTO {
        try {
            return bookFacade.createBook(request)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusExceptionFactory.createValidationMessageResponseException(e.message ?: "Unknown error.")
        }
    }

    @PutMapping("/entry")
    fun updateBook(@RequestBody request: UpdateBookInstructionTO): BookTO {
        try {
            return bookFacade.updateBook(request)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusExceptionFactory.createValidationMessageResponseException(e.message ?: "Unknown error.")
        }
    }

    // @DeleteMapping does not support request body
    @PostMapping("/entry/delete")
    fun deleteBook(@RequestBody request: DeleteBookInstructionTO) {
        try {
            bookFacade.deleteBook(request)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusExceptionFactory.createValidationMessageResponseException(e.message ?: "Unknown error.")
        }
    }
}
