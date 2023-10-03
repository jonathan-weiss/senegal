package org.codeblessing.senegal.frontendapi.controller.author
            
import org.codeblessing.senegal.domain.author.AuthorId
import org.codeblessing.senegal.domain.author.UpdateAuthorInstruction

import org.codeblessing.senegal.frontendapi.controller.commons.UuidTO

data class UpdateAuthorInstructionTO(
    val authorId: org.codeblessing.senegal.domain.author.AuthorId,
    val firstname: String,
    val lastname: String,
) {
    fun toDomain() = org.codeblessing.senegal.domain.author.UpdateAuthorInstruction(
        authorId = this.authorId,
        firstname = this.firstname,
        lastname = this.lastname,
    )
}    
