package org.codeblessing.senegal.domain.book

import org.codeblessing.senegal.domain.author.AuthorId


class SearchBookInstruction(
    val bookId: org.codeblessing.senegal.domain.book.BookId?,
    val bookName: String?,
    val mainAuthorId: org.codeblessing.senegal.domain.author.AuthorId?
)
