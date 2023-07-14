package ch.senegal.example.frontendapi.controller.author

import ch.senegal.example.frontendapi.API
import ch.senegal.example.frontendapi.controller.ResponseStatusExceptionFactory
import ch.senegal.example.frontendapi.controller.commons.UuidTO
import ch.senegal.example.frontendapi.facade.AuthorFacade
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("$API/authors")
class AuthorController(
    private val authorFacade: AuthorFacade
) {

    @GetMapping("/entry/{authorId}")
    fun getBook(@PathVariable("authorId") authorId: UUID): AuthorTO {
        try {
            return authorFacade
                .getAuthorById(UuidTO(authorId))
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusExceptionFactory.createValidationMessageResponseException(e.message ?: "Unknown error.")
        }
    }

    @GetMapping("/all")
    fun getBooks(): List<AuthorTO> {
        try {
            return authorFacade.getAllAuthors()
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusExceptionFactory.createValidationMessageResponseException(e.message ?: "Unknown error.")
        }
    }
}
