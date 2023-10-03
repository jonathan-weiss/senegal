package org.codeblessing.senegal.domain.book

import org.codeblessing.senegal.domain.author.AuthorId


class CreateBookInstruction(
    val bookName: String,
    val mainAuthorId: org.codeblessing.senegal.domain.author.AuthorId
)
