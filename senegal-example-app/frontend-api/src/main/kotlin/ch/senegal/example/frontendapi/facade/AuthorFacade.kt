package ch.senegal.example.frontendapi.facade

import ch.senegal.example.domain.author.AuthorService
import ch.senegal.example.domain.author.AuthorId
import ch.senegal.example.frontendapi.controller.author.AuthorTO
import ch.senegal.example.frontendapi.controller.author.CreateAuthorInstructionTO
import ch.senegal.example.frontendapi.controller.author.DeleteAuthorInstructionTO
import ch.senegal.example.frontendapi.controller.author.UpdateAuthorInstructionTO
import ch.senegal.example.frontendapi.controller.commons.UuidTO
import org.springframework.stereotype.Service

@Service
class AuthorFacade(
    private val service: AuthorService,
) {

    fun getAuthorById(id: UuidTO): AuthorTO {
        val domainInstance = service.getAuthor(AuthorId(id.uuid))
        return AuthorTO.fromDomain(domainInstance)
    }

    fun getAllAuthor(): List<AuthorTO> {
        return service.getListOfAllAuthor().map { AuthorTO.fromDomain(it) }
    }

    fun createAuthor(instruction: CreateAuthorInstructionTO): AuthorTO {
        val createdAuthor = service.createAuthor(instruction.toDomain())
        return AuthorTO.fromDomain(createdAuthor)
    }

    fun updateAuthor(instruction: UpdateAuthorInstructionTO): AuthorTO {
        val updatedAuthor = service.updateAuthor(instruction.toDomain())
        return AuthorTO.fromDomain(updatedAuthor)
    }

    fun deleteAuthor(instruction: DeleteAuthorInstructionTO) {
        service.deleteAuthor(instruction.toDomain())
    }
}
