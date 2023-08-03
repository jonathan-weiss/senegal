package ch.senegal.example.frontendapi.controller.book

import ch.senegal.example.domain.author.Author
import ch.senegal.example.domain.author.AuthorId

import ch.senegal.example.frontendapi.controller.commons.UuidTO

data class BookAuthorDescriptionTO(
    val authorId: AuthorId,
    val firstname: String,
    val lastname: String,
) {
    companion object {
        internal fun fromDomain(domainInstance: Author) = BookAuthorDescriptionTO(
            authorId = domainInstance.authorId,
            firstname = domainInstance.firstname,
            lastname = domainInstance.lastname,
        )
    }
}
