package org.codeblessing.senegal.frontendapi.facade

import org.springframework.stereotype.Service

@Service
class AuthorFacade(
    private val service: org.codeblessing.senegal.domain.author.AuthorService,
) {

    fun getAuthorById(id: org.codeblessing.senegal.frontendapi.controller.commons.UuidTO): org.codeblessing.senegal.frontendapi.controller.author.AuthorTO {
        val domainInstance = service.getAuthor(org.codeblessing.senegal.domain.author.AuthorId(id.uuid))
        return org.codeblessing.senegal.frontendapi.controller.author.AuthorTO.fromDomain(domainInstance)
    }

    fun getAllAuthor(): List<org.codeblessing.senegal.frontendapi.controller.author.AuthorTO> {
        return service.getListOfAllAuthor().map { org.codeblessing.senegal.frontendapi.controller.author.AuthorTO.fromDomain(it) }
    }

    fun getAllAuthorFiltered(searchTerm: String): List<org.codeblessing.senegal.frontendapi.controller.author.AuthorTO> {
        return service.getListOfFilteredAuthor(searchTerm).map { org.codeblessing.senegal.frontendapi.controller.author.AuthorTO.fromDomain(it) }
    }

    fun createAuthor(instruction: org.codeblessing.senegal.frontendapi.controller.author.CreateAuthorInstructionTO): org.codeblessing.senegal.frontendapi.controller.author.AuthorTO {
        val createdAuthor = service.createAuthor(instruction.toDomain())
        return org.codeblessing.senegal.frontendapi.controller.author.AuthorTO.fromDomain(createdAuthor)
    }

    fun updateAuthor(instruction: org.codeblessing.senegal.frontendapi.controller.author.UpdateAuthorInstructionTO): org.codeblessing.senegal.frontendapi.controller.author.AuthorTO {
        val updatedAuthor = service.updateAuthor(instruction.toDomain())
        return org.codeblessing.senegal.frontendapi.controller.author.AuthorTO.fromDomain(updatedAuthor)
    }

    fun deleteAuthor(instruction: org.codeblessing.senegal.frontendapi.controller.author.DeleteAuthorInstructionTO) {
        service.deleteAuthor(instruction.toDomain())
    }

    fun searchAllAuthor(searchParams: org.codeblessing.senegal.frontendapi.controller.author.SearchAuthorInstructionTO): List<org.codeblessing.senegal.frontendapi.controller.author.AuthorTO> {
        return service.searchAllAuthor(searchParams.toDomain()).map { org.codeblessing.senegal.frontendapi.controller.author.AuthorTO.fromDomain(it) }
    }
}
