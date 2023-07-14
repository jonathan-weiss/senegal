package ch.senegal.example.domain.author

import ch.senegal.example.shareddomain.AuthorId
import org.springframework.stereotype.Service

@Service
class AuthorService(
    private val repository: AuthorRepository,
) {

    fun getAuthor(authorId: AuthorId): Author {
        return repository.fetchAuthorById(authorId)
    }

    fun getAuthors(): List<Author> {
        return repository.fetchAllAuthors()
    }


}
