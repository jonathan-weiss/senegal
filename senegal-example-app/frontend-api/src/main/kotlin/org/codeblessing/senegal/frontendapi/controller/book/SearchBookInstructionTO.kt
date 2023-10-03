package org.codeblessing.senegal.frontendapi.controller.book

import org.codeblessing.senegal.domain.author.AuthorId
import org.codeblessing.senegal.domain.book.BookId
import org.codeblessing.senegal.domain.book.SearchBookInstruction
import org.codeblessing.senegal.frontendapi.controller.commons.UuidTO

data class SearchBookInstructionTO(
    val bookId: org.codeblessing.senegal.domain.book.BookId?,
    val bookName: String?,
    val mainAuthorId: org.codeblessing.senegal.domain.author.AuthorId?,
) {
    fun toDomain() = org.codeblessing.senegal.domain.book.SearchBookInstruction(
        bookId = this.bookId,
        bookName = this.bookName,
        mainAuthorId = this.mainAuthorId,
    )

}
