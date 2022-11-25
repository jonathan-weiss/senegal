package ch.senegal.example.service.book

import ch.senegal.example.shareddomain.BookId

class UpdateBookInstruction(
    val bookId: BookId,
    val bookName: String,
)
