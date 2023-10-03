package org.codeblessing.senegal.frontendapi.controller.author
            
import org.codeblessing.senegal.domain.author.CreateAuthorInstruction

data class CreateAuthorInstructionTO(
    val firstname: String,
    val lastname: String,
) {
    fun toDomain() = org.codeblessing.senegal.domain.author.CreateAuthorInstruction(
        firstname = this.firstname,
        lastname = this.lastname,
    )
}    
