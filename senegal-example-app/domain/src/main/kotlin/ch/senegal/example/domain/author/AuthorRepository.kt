package ch.senegal.example.domain.author

import ch.senegal.example.shareddomain.AuthorId

interface AuthorRepository {
    fun fetchAuthorById(authorId: AuthorId): Author
    fun fetchAllAuthors(): List<Author>
}
