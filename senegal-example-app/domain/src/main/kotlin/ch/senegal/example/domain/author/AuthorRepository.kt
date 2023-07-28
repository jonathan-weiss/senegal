package ch.senegal.example.domain.author

import ch.senegal.example.domain.author.Author
import ch.senegal.example.domain.author.AuthorId

interface AuthorRepository {
    fun fetchAuthorById(authorId: AuthorId): Author
    fun fetchAllAuthor(): List<Author>
    fun fetchAllAuthorFiltered(searchTerm: String): List<Author>

    fun insertAuthor(domainInstance: Author)
    fun updateAuthor(domainInstance: Author)
    fun deleteAuthor(domainInstance: Author)
}
