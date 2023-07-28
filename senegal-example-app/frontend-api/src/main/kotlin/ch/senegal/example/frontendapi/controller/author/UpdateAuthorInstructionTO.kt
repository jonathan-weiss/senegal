package ch.senegal.example.frontendapi.controller.author
            
import ch.senegal.example.domain.author.AuthorId
import ch.senegal.example.domain.author.UpdateAuthorInstruction

import ch.senegal.example.frontendapi.controller.commons.UuidTO

data class UpdateAuthorInstructionTO(
    val authorId: UuidTO,
    val firstname: kotlin.String,
    val lastname: kotlin.String,
) {
    fun toDomain() = UpdateAuthorInstruction(
        authorId = AuthorId(this.authorId.uuid),
        firstname = this.firstname,
        lastname = this.lastname,
    )
}    
