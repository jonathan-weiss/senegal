package ch.senegal.example.frontendapi.controller.book

import ch.senegal.example.domain.author.AuthorId
import ch.senegal.example.domain.book.*
import ch.senegal.example.frontendapi.controller.commons.UuidTO
import ch.senegal.example.frontendapi.controller.author.AuthorTO


data class BookTO(
    val bookId: UuidTO,
    val bookName: String,
    val mainAuthor: AuthorTO,
) {

    companion object {
        internal fun fromDomain(it: Book) = BookTO(
            bookId = UuidTO(it.bookId.value),
            bookName = it.bookName,
            mainAuthor = AuthorTO.fromDomain(it.mainAuthor)
        )
    }
}

data class CreateBookInstructionTO(
    val bookName: String,
    val mainAuthorId: UuidTO,
) {
    fun toDomain() = CreateBookInstruction(
        bookName = this.bookName,
        mainAuthorId = AuthorId(mainAuthorId.uuid)
    )
}

data class UpdateBookInstructionTO(
    val bookId: UuidTO,
    val bookName: String,
    val mainAuthorId: UuidTO,
) {
    fun toDomain() = UpdateBookInstruction(
        bookId = BookId(this.bookId.uuid),
        bookName = this.bookName,
        mainAuthorId = AuthorId(this.mainAuthorId.uuid)
    )
}

class DeleteBookInstructionTO(
    val bookId: UuidTO,
) {
    fun toDomain() = DeleteBookInstruction(
        bookId = BookId(this.bookId.uuid),
    )
}
