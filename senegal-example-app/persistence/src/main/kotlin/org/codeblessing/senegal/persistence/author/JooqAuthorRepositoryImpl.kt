package org.codeblessing.senegal.persistence.author

import org.codeblessing.senegal.domain.author.Author
import org.codeblessing.senegal.domain.author.AuthorRepository
import org.codeblessing.senegal.domain.author.AuthorId
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Repository


@Repository
class JooqAuthorRepositoryImpl(
    private val jooqDsl: DSLContext,
) : org.codeblessing.senegal.domain.author.AuthorRepository {

    override fun fetchAuthorById(authorId: org.codeblessing.senegal.domain.author.AuthorId): org.codeblessing.senegal.domain.author.Author {
        return jooqDsl
            .selectFrom(AuthorDsl.TABLE)
            .where(AuthorDsl.TABLE.AUTHOR_ID.eq(authorId.value))
            .fetchOne(this::toDomain)
            ?: throw RuntimeException("Author with id $authorId not found")
    }

    override fun fetchAllAuthor(): List<org.codeblessing.senegal.domain.author.Author> {
        return jooqDsl
            .selectFrom(AuthorDsl.TABLE)
            .fetch(this::toDomain)
    }

    override fun fetchAllAuthorFiltered(searchTerm: String): List<org.codeblessing.senegal.domain.author.Author> {
        return jooqDsl
            .selectFrom(AuthorDsl.TABLE)
            .where(AuthorDsl.TABLE.FIRSTNAME.like("%$searchTerm%"))
            .or(AuthorDsl.TABLE.LASTNAME.like("%$searchTerm%"))
            // .or(AuthorDsl.TABLE.AUTHOR_ID.like("%$searchTerm%"))
            .fetch(this::toDomain)
    }

    override fun insertAuthor(domainInstance: org.codeblessing.senegal.domain.author.Author) {
        jooqDsl.insertInto(AuthorDsl.TABLE)
            .set(AuthorDsl.TABLE.AUTHOR_ID, domainInstance.authorId.value)
            .set(AuthorDsl.TABLE.FIRSTNAME, domainInstance.firstname)
            .set(AuthorDsl.TABLE.LASTNAME, domainInstance.lastname)
            .execute()
    }

    override fun updateAuthor(domainInstance: org.codeblessing.senegal.domain.author.Author) {
        jooqDsl.update(AuthorDsl.TABLE)
            .set(AuthorDsl.TABLE.FIRSTNAME, domainInstance.firstname)
            .set(AuthorDsl.TABLE.LASTNAME, domainInstance.lastname)
            .where(AuthorDsl.TABLE.AUTHOR_ID.eq(domainInstance.authorId.value))
            .execute()
    }

    override fun deleteAuthor(domainInstance: org.codeblessing.senegal.domain.author.Author) {
        jooqDsl.deleteFrom(AuthorDsl.TABLE)
            .where(AuthorDsl.TABLE.AUTHOR_ID.eq(domainInstance.authorId.value))
            .execute()
    }

    private fun toDomain(record: Record): org.codeblessing.senegal.domain.author.Author {
        return org.codeblessing.senegal.domain.author.Author(
            authorId = org.codeblessing.senegal.domain.author.AuthorId(record.get(AuthorDsl.TABLE.AUTHOR_ID)),
            firstname = record.get(AuthorDsl.TABLE.FIRSTNAME),
            lastname = record.get(AuthorDsl.TABLE.LASTNAME),
        )
    }
}
