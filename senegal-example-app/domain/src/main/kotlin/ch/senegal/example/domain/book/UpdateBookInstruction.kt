package ch.senegal.example.domain.book

import ch.senegal.example.shareddomain.AuthorId
import ch.senegal.example.shareddomain.BookId

class UpdateBookInstruction(
    val bookId: BookId,
    val bookName: String,
    val mainAuthorId: AuthorId
)
