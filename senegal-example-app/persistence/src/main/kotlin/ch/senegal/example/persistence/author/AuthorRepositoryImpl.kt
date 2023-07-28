package ch.senegal.example.persistence.author

import ch.senegal.example.domain.author.Author
import ch.senegal.example.domain.author.AuthorRepository
import ch.senegal.example.domain.author.AuthorId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository


@Repository
class AuthorRepositoryImpl(
private val jpaRepository: AuthorJpaRepository
) : AuthorRepository {

    override fun fetchAuthorById(authorId: AuthorId): Author {
        val entry = jpaRepository.findByIdOrNull(authorId.value) ?: throw RuntimeException("Author with id \$authorId not found")
        return entry.toDomain()
    }

    override fun fetchAllAuthor(): List<Author> {
        return jpaRepository.findAll().map { it.toDomain() }
    }

    override fun insertAuthor(domainInstance: Author) {
        jpaRepository.save(AuthorJpaEntity.fromDomain(domainInstance))
    }

    override fun updateAuthor(domainInstance: Author) {
        jpaRepository.save(AuthorJpaEntity.fromDomain(domainInstance))
    }

    override fun deleteAuthor(domainInstance: Author) {
        jpaRepository.delete(AuthorJpaEntity.fromDomain(domainInstance))
    }
}
