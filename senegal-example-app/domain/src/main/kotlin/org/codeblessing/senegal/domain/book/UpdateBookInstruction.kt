package org.codeblessing.senegal.domain.book

import org.codeblessing.senegal.domain.author.AuthorId


class UpdateBookInstruction(
    val bookId: org.codeblessing.senegal.domain.book.BookId,
    val bookName: String,
    val mainAuthorId: org.codeblessing.senegal.domain.author.AuthorId
)
