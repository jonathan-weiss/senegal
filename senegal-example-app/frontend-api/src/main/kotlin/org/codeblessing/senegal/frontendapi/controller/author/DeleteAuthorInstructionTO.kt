package org.codeblessing.senegal.frontendapi.controller.author
            
import org.codeblessing.senegal.domain.author.AuthorId
import org.codeblessing.senegal.domain.author.DeleteAuthorInstruction

import org.codeblessing.senegal.frontendapi.controller.commons.UuidTO

data class DeleteAuthorInstructionTO(
    val authorId: org.codeblessing.senegal.domain.author.AuthorId,
){
    fun toDomain() = org.codeblessing.senegal.domain.author.DeleteAuthorInstruction(
        authorId = this.authorId,
    )
}
