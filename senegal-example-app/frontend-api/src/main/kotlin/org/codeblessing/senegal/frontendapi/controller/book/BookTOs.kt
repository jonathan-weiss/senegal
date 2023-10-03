package org.codeblessing.senegal.frontendapi.controller.book


data class BookTO(
    val bookId: org.codeblessing.senegal.domain.book.BookId,
    val bookName: String,
    val mainAuthor: org.codeblessing.senegal.frontendapi.controller.book.BookAuthorDescriptionTO,
) {

    companion object {
        internal fun fromDomain(it: org.codeblessing.senegal.domain.book.Book) = BookTO(
            bookId = it.bookId,
            bookName = it.bookName,
            mainAuthor = org.codeblessing.senegal.frontendapi.controller.book.BookAuthorDescriptionTO.fromDomain(it.mainAuthor)
        )
    }
}

data class CreateBookInstructionTO(
    val bookName: String,
    val mainAuthorId: org.codeblessing.senegal.domain.author.AuthorId,
) {
    fun toDomain() = org.codeblessing.senegal.domain.book.CreateBookInstruction(
        bookName = this.bookName,
        mainAuthorId = mainAuthorId,
    )
}

data class UpdateBookInstructionTO(
    val bookId: org.codeblessing.senegal.domain.book.BookId,
    val bookName: String,
    val mainAuthorId: org.codeblessing.senegal.domain.author.AuthorId,
) {
    fun toDomain() = org.codeblessing.senegal.domain.book.UpdateBookInstruction(
        bookId = this.bookId,
        bookName = this.bookName,
        mainAuthorId = this.mainAuthorId
    )
}

class DeleteBookInstructionTO(
    val bookId: org.codeblessing.senegal.domain.book.BookId,
) {
    fun toDomain() = org.codeblessing.senegal.domain.book.DeleteBookInstruction(
        bookId = this.bookId,
    )
}
