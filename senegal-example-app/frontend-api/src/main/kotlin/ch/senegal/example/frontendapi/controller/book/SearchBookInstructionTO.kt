package ch.senegal.example.frontendapi.controller.book

import ch.senegal.example.domain.author.AuthorId
import ch.senegal.example.domain.book.BookId
import ch.senegal.example.domain.book.SearchBookInstruction
import ch.senegal.example.frontendapi.controller.commons.UuidTO

data class SearchBookInstructionTO(
    val bookId: BookId?,
    val bookName: String?,
    val mainAuthorId: AuthorId?,
) {
    fun toDomain() = SearchBookInstruction(
        bookId = this.bookId,
        bookName = this.bookName,
        mainAuthorId = this.mainAuthorId,
    )

}
