package ch.senegal.example.frontendapi.controller.author
            
import ch.senegal.example.domain.author.CreateAuthorInstruction

data class CreateAuthorInstructionTO(
    val firstname: String,
    val lastname: String,
) {
    fun toDomain() = CreateAuthorInstruction(
        firstname = this.firstname,
        lastname = this.lastname,
    )
}    
