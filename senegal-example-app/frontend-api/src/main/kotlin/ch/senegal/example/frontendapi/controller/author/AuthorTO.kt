package ch.senegal.example.frontendapi.controller.author

import ch.senegal.example.domain.author.Author
import ch.senegal.example.domain.author.AuthorId

import ch.senegal.example.frontendapi.controller.commons.UuidTO

data class AuthorTO(
    val authorId: AuthorId,
    val firstname: String,
    val lastname: String,
) {
    companion object {
        internal fun fromDomain(domainInstance: Author) = AuthorTO(
            authorId = domainInstance.authorId,
            firstname = domainInstance.firstname,
            lastname = domainInstance.lastname,
        )
    }
}
