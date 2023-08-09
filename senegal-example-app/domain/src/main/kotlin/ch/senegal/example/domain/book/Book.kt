package ch.senegal.example.domain.book

import ch.senegal.example.domain.author.Author


class Book(
    val bookId: BookId,
    var bookName: String,
    var mainAuthor: AuthorDescription,
) {
    companion object {
        internal fun create(instruction: CreateBookInstruction, author: AuthorDescription) = Book(
            bookId = BookId.random(),
            bookName = instruction.bookName,
            mainAuthor = author
        )
    }

    internal fun update(instruction: UpdateBookInstruction, author: AuthorDescription) {
        this.bookName = instruction.bookName
        this.mainAuthor = author
    }
}
