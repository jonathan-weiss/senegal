package org.codeblessing.senegal.domain.book

import org.codeblessing.senegal.domain.author.AuthorId

class AuthorDescription(
    val authorId: org.codeblessing.senegal.domain.author.AuthorId,
    var firstname: String,
    var lastname: String,

    )  {
}
