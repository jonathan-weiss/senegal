package ch.senegal.example.domain.author

class Author(
    val authorId: AuthorId,
    var firstname: kotlin.String,

    var lastname: kotlin.String,

    )  {
    companion object {
        internal fun create(instruction: CreateAuthorInstruction) = Author(
            authorId = AuthorId.random(),
            firstname = instruction.firstname,
            lastname = instruction.lastname,
        )
    }

    internal fun update(instruction: UpdateAuthorInstruction) {
        this.firstname = instruction.firstname
        
        this.lastname = instruction.lastname
        
    }
}
