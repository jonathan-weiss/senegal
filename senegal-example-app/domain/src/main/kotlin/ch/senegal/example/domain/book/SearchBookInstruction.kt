package ch.senegal.example.domain.book

import ch.senegal.example.domain.author.AuthorId


class SearchBookInstruction(
    val bookId: BookId?,
    val bookName: String?,
    val mainAuthorId: AuthorId?
)