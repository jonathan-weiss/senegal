package ch.senegal.example.frontendapi.controller.author

import ch.senegal.example.domain.author.AuthorId
import ch.senegal.example.domain.author.SearchAuthorInstruction
import ch.senegal.example.frontendapi.controller.commons.UuidTO

data class SearchAuthorInstructionTO(
    val authorId: UuidTO?,
    val firstname: String?,
    val lastname: String?,
) {
    fun toDomain() = SearchAuthorInstruction(
        authorId = this.authorId?.let { AuthorId(it.uuid) },
        firstname = this.firstname,
        lastname = this.lastname,
    )

}
