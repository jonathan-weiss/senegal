package org.codeblessing.senegal.frontendapi.controller.author

import org.codeblessing.senegal.domain.author.AuthorId
import org.codeblessing.senegal.domain.author.SearchAuthorInstruction
import org.codeblessing.senegal.frontendapi.controller.commons.UuidTO

data class SearchAuthorInstructionTO(
    val authorId: org.codeblessing.senegal.domain.author.AuthorId?,
    val firstname: String?,
    val lastname: String?,
) {
    fun toDomain() = org.codeblessing.senegal.domain.author.SearchAuthorInstruction(
        authorId = this.authorId,
        firstname = this.firstname,
        lastname = this.lastname,
    )

}
