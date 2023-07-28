package ch.senegal.example.domain.book

import ch.senegal.example.domain.author.AuthorId


class CreateBookInstruction(
    val bookName: String,
    val mainAuthorId: AuthorId
)
