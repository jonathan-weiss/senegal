package ch.senegal.example.frontendapi.controller.author
            
import ch.senegal.example.domain.author.CreateAuthorInstruction

data class CreateAuthorInstructionTO(
    val firstname: kotlin.String,
    val lastname: kotlin.String,
) {
    fun toDomain() = CreateAuthorInstruction(
        firstname = this.firstname,
        lastname = this.lastname,
    )
}    
