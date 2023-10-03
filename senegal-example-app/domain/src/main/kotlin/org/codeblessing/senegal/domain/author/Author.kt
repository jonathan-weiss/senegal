package org.codeblessing.senegal.domain.author

class Author(
    val authorId: org.codeblessing.senegal.domain.author.AuthorId,
    var firstname: kotlin.String,

    var lastname: kotlin.String,

    )  {
    companion object {
        internal fun create(instruction: org.codeblessing.senegal.domain.author.CreateAuthorInstruction) = Author(
            authorId = org.codeblessing.senegal.domain.author.AuthorId.random(),
            firstname = instruction.firstname,
            lastname = instruction.lastname,
        )
    }

    internal fun update(instruction: org.codeblessing.senegal.domain.author.UpdateAuthorInstruction) {
        this.firstname = instruction.firstname
        
        this.lastname = instruction.lastname
        
    }
}
