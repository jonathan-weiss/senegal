package ch.senegal.example.domain.author

import ch.senegal.example.shareddomain.AuthorId

class Author(
    val authorId: AuthorId,
    var firstname: String,
    var lastname: String,
)
