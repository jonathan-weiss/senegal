package org.codeblessing.senegal.frontendapi.controller.author

import org.codeblessing.senegal.domain.author.Author
import org.codeblessing.senegal.domain.author.AuthorId

import org.codeblessing.senegal.frontendapi.controller.commons.UuidTO

data class AuthorTO(
    val authorId: org.codeblessing.senegal.domain.author.AuthorId,
    val firstname: String,
    val lastname: String,
) {
    companion object {
        internal fun fromDomain(domainInstance: org.codeblessing.senegal.domain.author.Author) = AuthorTO(
            authorId = domainInstance.authorId,
            firstname = domainInstance.firstname,
            lastname = domainInstance.lastname,
        )
    }
}
