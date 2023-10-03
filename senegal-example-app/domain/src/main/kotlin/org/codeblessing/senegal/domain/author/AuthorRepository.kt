package org.codeblessing.senegal.domain.author

import org.codeblessing.senegal.domain.author.Author
import org.codeblessing.senegal.domain.author.AuthorId

interface AuthorRepository {
    fun fetchAuthorById(authorId: org.codeblessing.senegal.domain.author.AuthorId): org.codeblessing.senegal.domain.author.Author
    fun fetchAllAuthor(): List<org.codeblessing.senegal.domain.author.Author>
    fun fetchAllAuthorFiltered(searchTerm: String): List<org.codeblessing.senegal.domain.author.Author>

    fun insertAuthor(domainInstance: org.codeblessing.senegal.domain.author.Author)
    fun updateAuthor(domainInstance: org.codeblessing.senegal.domain.author.Author)
    fun deleteAuthor(domainInstance: org.codeblessing.senegal.domain.author.Author)
}
