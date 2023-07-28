package ch.senegal.example.domain.author

import ch.senegal.example.domain.author.AuthorId

class UpdateAuthorInstruction(
    val authorId: AuthorId,
    val firstname: kotlin.String,
    val lastname: kotlin.String,
)
