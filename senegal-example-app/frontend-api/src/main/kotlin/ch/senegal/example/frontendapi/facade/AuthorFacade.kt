package ch.senegal.example.frontendapi.facade

import ch.senegal.example.domain.author.AuthorService
import ch.senegal.example.frontendapi.controller.author.AuthorTO
import ch.senegal.example.frontendapi.controller.commons.UuidTO
import ch.senegal.example.shareddomain.AuthorId
import org.springframework.stereotype.Service

@Service
class AuthorFacade(
    private val authorService: AuthorService
) {

    fun getAuthorById(id: UuidTO): AuthorTO {
        val author = authorService.getAuthor(AuthorId(id.uuid))
        return AuthorTO.fromDomain(author)
    }

    fun getAllAuthors(): List<AuthorTO> {
        return authorService.getAuthors().map { AuthorTO.fromDomain(it) }
    }
}
