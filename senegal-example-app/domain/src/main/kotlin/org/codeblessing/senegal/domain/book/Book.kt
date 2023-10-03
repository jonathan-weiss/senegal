package org.codeblessing.senegal.domain.book

import org.codeblessing.senegal.domain.author.Author


class Book(
    val bookId: org.codeblessing.senegal.domain.book.BookId,
    var bookName: String,
    var mainAuthor: org.codeblessing.senegal.domain.book.AuthorDescription,
) {
    companion object {
        internal fun create(instruction: org.codeblessing.senegal.domain.book.CreateBookInstruction, author: org.codeblessing.senegal.domain.book.AuthorDescription) = Book(
            bookId = org.codeblessing.senegal.domain.book.BookId.random(),
            bookName = instruction.bookName,
            mainAuthor = author
        )
    }

    internal fun update(instruction: org.codeblessing.senegal.domain.book.UpdateBookInstruction, author: org.codeblessing.senegal.domain.book.AuthorDescription) {
        this.bookName = instruction.bookName
        this.mainAuthor = author
    }
}
