package ch.senegal.example.frontendapi.controller.book

import ch.senegal.example.domain.author.AuthorId
import ch.senegal.example.domain.book.*
import ch.senegal.example.frontendapi.controller.commons.UuidTO
import ch.senegal.example.frontendapi.controller.author.AuthorTO


data class BookTO(
    val bookId: BookId,
    val bookName: String,
    val mainAuthor: BookAuthorDescriptionTO,
) {

    companion object {
        internal fun fromDomain(it: Book) = BookTO(
            bookId = it.bookId,
            bookName = it.bookName,
            mainAuthor = BookAuthorDescriptionTO.fromDomain(it.mainAuthor)
        )
    }
}

data class CreateBookInstructionTO(
    val bookName: String,
    val mainAuthorId: AuthorId,
) {
    fun toDomain() = CreateBookInstruction(
        bookName = this.bookName,
        mainAuthorId = mainAuthorId,
    )
}

data class UpdateBookInstructionTO(
    val bookId: BookId,
    val bookName: String,
    val mainAuthorId: AuthorId,
) {
    fun toDomain() = UpdateBookInstruction(
        bookId = this.bookId,
        bookName = this.bookName,
        mainAuthorId = this.mainAuthorId
    )
}

class DeleteBookInstructionTO(
    val bookId: BookId,
) {
    fun toDomain() = DeleteBookInstruction(
        bookId = this.bookId,
    )
}
