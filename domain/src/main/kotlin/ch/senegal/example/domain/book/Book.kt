package ch.senegal.example.domain.book

import ch.senegal.example.service.book.CreateBookInstruction
import ch.senegal.example.service.book.UpdateBookInstruction
import ch.senegal.example.shareddomain.BookId

class Book(
    val bookId: BookId,
    var bookName: String,
) {
    companion object {
        internal fun create(instruction: CreateBookInstruction) = Book(
            bookId = BookId.random(),
            bookName = instruction.bookName,
        )
    }

    internal fun update(instruction: UpdateBookInstruction) {
        this.bookName = instruction.bookName
    }
}
