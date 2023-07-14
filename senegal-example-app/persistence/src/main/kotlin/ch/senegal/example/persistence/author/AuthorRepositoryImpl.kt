package ch.senegal.example.persistence.author

import ch.senegal.example.domain.author.Author
import ch.senegal.example.domain.author.AuthorRepository
import ch.senegal.example.shareddomain.AuthorId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class AuthorRepositoryImpl(
    private val jpaRepository: AuthorJpaRepository
) : AuthorRepository {

    override fun fetchAuthorById(authorId: AuthorId): Author {
        val entry = jpaRepository.findByIdOrNull(authorId.value) ?: throw RuntimeException("Author with id $authorId not found")
        return entry.toDomain()
    }

    override fun fetchAllAuthors(): List<Author> {
        return jpaRepository.findAll().map { it.toDomain() }
    }
}
