package ch.senegal.example.frontendapi.controller.book

import ch.senegal.example.domain.author.AuthorId
import ch.senegal.example.domain.book.BookId
import ch.senegal.example.domain.book.SearchBookInstruction
import ch.senegal.example.frontendapi.controller.commons.UuidTO

data class SearchBookInstructionTO(
    val bookId: UuidTO?,
    val bookName: String?,
    val mainAuthorId: UuidTO?,
) {
    fun toDomain() = SearchBookInstruction(
        bookId = this.bookId?.let { BookId(it.uuid) },
        bookName = this.bookName,
        mainAuthorId = this.mainAuthorId?.let { AuthorId(it.uuid) }
    )

}
