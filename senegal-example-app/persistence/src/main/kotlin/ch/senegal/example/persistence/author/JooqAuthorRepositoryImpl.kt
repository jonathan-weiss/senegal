package ch.senegal.example.persistence.author

import ch.senegal.example.domain.author.Author
import ch.senegal.example.domain.author.AuthorRepository
import ch.senegal.example.domain.author.AuthorId
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Repository


@Repository
class JooqAuthorRepositoryImpl(
    private val jooqDsl: DSLContext,
) : AuthorRepository {

    override fun fetchAuthorById(authorId: AuthorId): Author {
        return jooqDsl
            .selectFrom(AuthorDsl.TABLE)
            .where(AuthorDsl.TABLE.AUTHOR_ID.eq(authorId.value))
            .fetchOne(this::toDomain)
            ?: throw RuntimeException("Author with id \$authorId not found")
    }

    override fun fetchAllAuthor(): List<Author> {
        return jooqDsl
            .selectFrom(AuthorDsl.TABLE)
            .fetch(this::toDomain)
    }

    override fun fetchAllAuthorFiltered(searchTerm: String): List<Author> {
        return jooqDsl
            .selectFrom(AuthorDsl.TABLE)
            .fetch(this::toDomain)
            // TODO Filter directly in the database by WHERE statement
            .filter {
                searchTerm.isEmpty()
                        || it.firstname.contains(searchTerm)
                        || it.lastname.contains(searchTerm)
                        || it.authorId.value.toString().contains(searchTerm)
            }

    }

    override fun insertAuthor(domainInstance: Author) {
        jooqDsl.insertInto(AuthorDsl.TABLE, AuthorDsl.TABLE.AUTHOR_ID, AuthorDsl.TABLE.FIRSTNAME, AuthorDsl.TABLE.LASTNAME)
            .values(domainInstance.authorId.value, domainInstance.firstname, domainInstance.lastname)
            .execute()
    }

    override fun updateAuthor(domainInstance: Author) {
        jooqDsl.update(AuthorDsl.TABLE)
            .set(AuthorDsl.TABLE.FIRSTNAME, domainInstance.firstname)
            .set(AuthorDsl.TABLE.LASTNAME, domainInstance.lastname)
            .where(AuthorDsl.TABLE.AUTHOR_ID.eq(domainInstance.authorId.value))
            .execute()
    }

    override fun deleteAuthor(domainInstance: Author) {
        jooqDsl.deleteFrom(AuthorDsl.TABLE)
            .where(AuthorDsl.TABLE.AUTHOR_ID.eq(domainInstance.authorId.value))
            .execute()
    }

    private fun toDomain(record: Record): Author {
        return Author(
            authorId = AuthorId(record.get(AuthorDsl.TABLE.AUTHOR_ID)),
            firstname = record.get(AuthorDsl.TABLE.FIRSTNAME),
            lastname = record.get(AuthorDsl.TABLE.LASTNAME),
        )
    }
}
