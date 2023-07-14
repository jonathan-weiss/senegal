package ch.senegal.example.frontendapi.controller.author

import ch.senegal.example.domain.author.Author
import ch.senegal.example.frontendapi.controller.commons.UuidTO

data class AuthorTO(
    val authorId: UuidTO,
    val firstname: String,
    val lastname: String,
) {

    companion object {
        internal fun fromDomain(it: Author) = AuthorTO(
            authorId = UuidTO(it.authorId.value),
            firstname = it.firstname,
            lastname = it.lastname,
        )
    }
}
