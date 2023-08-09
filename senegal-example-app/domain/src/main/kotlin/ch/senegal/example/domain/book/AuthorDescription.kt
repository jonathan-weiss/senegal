package ch.senegal.example.domain.book

import ch.senegal.example.domain.author.AuthorId

class AuthorDescription(
    val authorId: AuthorId,
    var firstname: String,
    var lastname: String,

    )  {
}
