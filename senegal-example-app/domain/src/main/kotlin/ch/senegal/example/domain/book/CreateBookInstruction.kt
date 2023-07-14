package ch.senegal.example.domain.book

import ch.senegal.example.shareddomain.AuthorId

class CreateBookInstruction(
    val bookName: String,
    val mainAuthorId: AuthorId
)
