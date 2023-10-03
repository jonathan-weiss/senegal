package org.codeblessing.senegal.persistence.book

import org.codeblessing.senegal.domain.author.AuthorId
import org.codeblessing.senegal.domain.book.AuthorDescription
import org.codeblessing.senegal.domain.book.Book
import org.codeblessing.senegal.domain.book.BookId
import org.codeblessing.senegal.domain.book.BookRepository
import org.codeblessing.senegal.persistence.author.AuthorDsl
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Repository


@Repository
class JooqBookRepositoryImpl(
    private val jooqDsl: DSLContext,
) : org.codeblessing.senegal.domain.book.BookRepository {

    override fun fetchBookById(bookId: org.codeblessing.senegal.domain.book.BookId): org.codeblessing.senegal.domain.book.Book {
        val bookRecord = jooqDsl
            .selectFrom(BookDsl.TABLE)
            .where(BookDsl.TABLE.BOOK_ID.eq(bookId.value))
            .fetchOne()
            ?: throw RuntimeException("Book with id $bookId not found")

        val mainAuthorId = org.codeblessing.senegal.domain.author.AuthorId(bookRecord.get(BookDsl.TABLE.MAIN_AUTHOR_ID))
        val authorDescriptions = authorDescriptions(listOf(mainAuthorId))
        return toDomain(bookRecord, authorDescriptions)
    }

    override fun fetchAllBooks(): List<org.codeblessing.senegal.domain.book.Book> {
        return toDomains(jooqDsl
            .selectFrom(BookDsl.TABLE)
            .fetch())
    }

    override fun fetchAllBooksByAuthor(authorId: org.codeblessing.senegal.domain.author.AuthorId): List<org.codeblessing.senegal.domain.book.Book> {
        return toDomains(jooqDsl
            .selectFrom(BookDsl.TABLE)
            .where(BookDsl.TABLE.MAIN_AUTHOR_ID.eq(authorId.value))
            .fetch())
    }

    override fun fetchAuthorDescriptionById(authorId: org.codeblessing.senegal.domain.author.AuthorId): org.codeblessing.senegal.domain.book.AuthorDescription {
        return authorDescription(authorId)
    }

    fun fetchAllBookFiltered(searchTerm: String): List<org.codeblessing.senegal.domain.book.Book> {
        return toDomains(jooqDsl
            .selectFrom(BookDsl.TABLE)
            .where(BookDsl.TABLE.BOOK_NAME.like("%$searchTerm%"))
            // .or(BookDsl.TABLE.BOOK_ID.like("%$searchTerm%"))
            .fetch())
    }

    override fun insertBook(book: org.codeblessing.senegal.domain.book.Book) {
        jooqDsl.insertInto(BookDsl.TABLE, BookDsl.TABLE.BOOK_ID, BookDsl.TABLE.BOOK_NAME, BookDsl.TABLE.MAIN_AUTHOR_ID)
            .values(book.bookId.value, book.bookName, book.mainAuthor.authorId.value)
            .execute()
    }

    override fun updateBook(book: org.codeblessing.senegal.domain.book.Book) {
        jooqDsl.update(BookDsl.TABLE)
            .set(BookDsl.TABLE.BOOK_NAME, book.bookName)
            .set(BookDsl.TABLE.MAIN_AUTHOR_ID, book.mainAuthor.authorId.value)
            .where(BookDsl.TABLE.BOOK_ID.eq(book.bookId.value))
            .execute()
    }

    override fun deleteBook(book: org.codeblessing.senegal.domain.book.Book) {
        jooqDsl.deleteFrom(BookDsl.TABLE)
            .where(BookDsl.TABLE.BOOK_ID.eq(book.bookId.value))
            .execute()
    }

    private fun toDomains(bookRecords: List<Record>): List<org.codeblessing.senegal.domain.book.Book> {
        val mainAuthorIds: List<org.codeblessing.senegal.domain.author.AuthorId> = bookRecords.map {
            org.codeblessing.senegal.domain.author.AuthorId(
                it.get(BookDsl.TABLE.MAIN_AUTHOR_ID)
            )
        }
        val mainAuthors: Map<org.codeblessing.senegal.domain.author.AuthorId, org.codeblessing.senegal.domain.book.AuthorDescription> = authorDescriptions(mainAuthorIds)
        return bookRecords.map { toDomain(bookRecord = it, mainAuthors = mainAuthors) }
    }

    private fun toDomain(bookRecord: Record, mainAuthors: Map<org.codeblessing.senegal.domain.author.AuthorId, org.codeblessing.senegal.domain.book.AuthorDescription>): org.codeblessing.senegal.domain.book.Book {
        val mainAuthorId = org.codeblessing.senegal.domain.author.AuthorId(bookRecord.get(BookDsl.TABLE.MAIN_AUTHOR_ID))
        return org.codeblessing.senegal.domain.book.Book(
            bookId = org.codeblessing.senegal.domain.book.BookId(bookRecord.get(BookDsl.TABLE.BOOK_ID)),
            bookName = bookRecord.get(BookDsl.TABLE.BOOK_NAME),
            mainAuthor = authorDescription(mainAuthorId, mainAuthors),
        )
    }

    private fun authorDescription(authorId: org.codeblessing.senegal.domain.author.AuthorId): org.codeblessing.senegal.domain.book.AuthorDescription {
        return authorDescription(authorId, authorDescriptions(listOf(authorId)))
    }


    private fun authorDescription(authorId: org.codeblessing.senegal.domain.author.AuthorId, authorDescriptions: Map<org.codeblessing.senegal.domain.author.AuthorId, org.codeblessing.senegal.domain.book.AuthorDescription>): org.codeblessing.senegal.domain.book.AuthorDescription {
        return authorDescriptions[authorId] ?: throw IllegalStateException("Author not found for id $authorId")
    }

    private fun authorDescriptions(authorIds: List<org.codeblessing.senegal.domain.author.AuthorId>): Map<org.codeblessing.senegal.domain.author.AuthorId, org.codeblessing.senegal.domain.book.AuthorDescription> {
        val authorRecords = jooqDsl
            .selectFrom(AuthorDsl.TABLE)
            .where(AuthorDsl.TABLE.AUTHOR_ID.`in`(authorIds.map { it.value }))
            .fetch()

        return authorRecords.map { record ->
            org.codeblessing.senegal.domain.book.AuthorDescription(
                authorId = org.codeblessing.senegal.domain.author.AuthorId(record.get(AuthorDsl.TABLE.AUTHOR_ID)),
                firstname = record.get(AuthorDsl.TABLE.FIRSTNAME),
                lastname = record.get(AuthorDsl.TABLE.LASTNAME),
            )
        }.associateBy { author -> author.authorId }
    }
}
