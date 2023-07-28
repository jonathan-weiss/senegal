package ch.senegal.example.frontendapi.controller.author
            
import ch.senegal.example.domain.author.AuthorId
import ch.senegal.example.domain.author.DeleteAuthorInstruction

import ch.senegal.example.frontendapi.controller.commons.UuidTO

data class DeleteAuthorInstructionTO(
    val authorId: UuidTO,
){
    fun toDomain() = DeleteAuthorInstruction(
        authorId = AuthorId(this.authorId.uuid),
    )
}