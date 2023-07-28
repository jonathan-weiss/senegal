package ch.senegal.example.frontendapi.controller.author

import ch.senegal.example.domain.author.Author

import ch.senegal.example.frontendapi.controller.commons.UuidTO

data class AuthorTO(
    val authorId: UuidTO,
    val firstname: kotlin.String,
    val lastname: kotlin.String,
) {
    companion object {
        internal fun fromDomain(domainInstance: Author) = AuthorTO(
            authorId = UuidTO(domainInstance.authorId.value),
            firstname = domainInstance.firstname,
            lastname = domainInstance.lastname,
        )
    }
}
