package ch.senegal.example.frontendapi.controller.author
            
import ch.senegal.example.domain.author.AuthorId
import ch.senegal.example.domain.author.UpdateAuthorInstruction

import ch.senegal.example.frontendapi.controller.commons.UuidTO

data class UpdateAuthorInstructionTO(
    val authorId: AuthorId,
    val firstname: String,
    val lastname: String,
) {
    fun toDomain() = UpdateAuthorInstruction(
        authorId = this.authorId,
        firstname = this.firstname,
        lastname = this.lastname,
    )
}    
