package org.codeblessing.senegal.domain.author

import org.codeblessing.senegal.domain.author.AuthorId

class UpdateAuthorInstruction(
    val authorId: org.codeblessing.senegal.domain.author.AuthorId,
    val firstname: kotlin.String,
    val lastname: kotlin.String,
)
