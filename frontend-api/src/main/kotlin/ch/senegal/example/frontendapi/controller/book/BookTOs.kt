package ch.senegal.example.frontendapi.controller.book

import ch.senegal.example.domain.book.Book
import ch.senegal.example.frontendapi.controller.commons.UuidTO
import ch.senegal.example.service.book.CreateBookInstruction
import ch.senegal.example.service.book.DeleteBookInstruction
import ch.senegal.example.service.book.UpdateBookInstruction
import ch.senegal.example.shareddomain.BookId

data class BookTO(
    val bookId: UuidTO,
    val bookName: String,
) {

    companion object {
        internal fun fromDomain(it: Book) = BookTO(
            bookId = UuidTO(it.bookId.value),
            bookName = it.bookName,
        )
    }
}

data class CreateBookInstructionTO(
    val bookName: String,
) {
    fun toDomain() = CreateBookInstruction(
        bookName = this.bookName
    )
}

data class UpdateBookInstructionTO(
    val bookId: UuidTO,
    val bookName: String,
) {
    fun toDomain() = UpdateBookInstruction(
        bookId = BookId(this.bookId.uuid),
        bookName = this.bookName,
    )
}

class DeleteBookInstructionTO(
    val bookId: UuidTO,
) {
    fun toDomain() = DeleteBookInstruction(
        bookId = BookId(this.bookId.uuid),
    )
}
