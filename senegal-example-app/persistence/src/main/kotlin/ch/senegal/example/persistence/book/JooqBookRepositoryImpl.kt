package ch.senegal.example.persistence.book

import ch.senegal.example.domain.author.AuthorId
import ch.senegal.example.domain.book.AuthorDescription
import ch.senegal.example.domain.book.Book
import ch.senegal.example.domain.book.BookId
import ch.senegal.example.domain.book.BookRepository
import ch.senegal.example.persistence.author.AuthorDsl
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Repository


@Repository
class JooqBookRepositoryImpl(
    private val jooqDsl: DSLContext,
) : BookRepository {

    override fun fetchBookById(bookId: BookId): Book {
        val bookRecord = jooqDsl
            .selectFrom(BookDsl.TABLE)
            .where(BookDsl.TABLE.BOOK_ID.eq(bookId.value))
            .fetchOne()
            ?: throw RuntimeException("Book with id $bookId not found")

        val mainAuthorId = AuthorId(bookRecord.get(BookDsl.TABLE.MAIN_AUTHOR_ID))
        val authorDescriptions = authorDescriptions(listOf(mainAuthorId))
        return toDomain(bookRecord, authorDescriptions)
    }

    override fun fetchAllBooks(): List<Book> {
        return toDomains(jooqDsl
            .selectFrom(BookDsl.TABLE)
            .fetch())
    }

    override fun fetchAllBooksByAuthor(authorId: AuthorId): List<Book> {
        return toDomains(jooqDsl
            .selectFrom(BookDsl.TABLE)
            .where(BookDsl.TABLE.MAIN_AUTHOR_ID.eq(authorId.value))
            .fetch())
    }

    override fun fetchAuthorDescriptionById(authorId: AuthorId): AuthorDescription {
        return authorDescription(authorId)
    }

    fun fetchAllBookFiltered(searchTerm: String): List<Book> {
        return toDomains(jooqDsl
            .selectFrom(BookDsl.TABLE)
            .where(BookDsl.TABLE.BOOK_NAME.like("%$searchTerm%"))
            // .or(BookDsl.TABLE.BOOK_ID.like("%$searchTerm%"))
            .fetch())
    }

    override fun insertBook(book: Book) {
        jooqDsl.insertInto(BookDsl.TABLE, BookDsl.TABLE.BOOK_ID, BookDsl.TABLE.BOOK_NAME, BookDsl.TABLE.MAIN_AUTHOR_ID)
            .values(book.bookId.value, book.bookName, book.mainAuthor.authorId.value)
            .execute()
    }

    override fun updateBook(book: Book) {
        jooqDsl.update(BookDsl.TABLE)
            .set(BookDsl.TABLE.BOOK_NAME, book.bookName)
            .set(BookDsl.TABLE.MAIN_AUTHOR_ID, book.mainAuthor.authorId.value)
            .where(BookDsl.TABLE.BOOK_ID.eq(book.bookId.value))
            .execute()
    }

    override fun deleteBook(book: Book) {
        jooqDsl.deleteFrom(BookDsl.TABLE)
            .where(BookDsl.TABLE.BOOK_ID.eq(book.bookId.value))
            .execute()
    }

    private fun toDomains(bookRecords: List<Record>): List<Book> {
        val mainAuthorIds: List<AuthorId> = bookRecords.map { AuthorId(it.get(BookDsl.TABLE.MAIN_AUTHOR_ID)) }
        val mainAuthors: Map<AuthorId, AuthorDescription> = authorDescriptions(mainAuthorIds)
        return bookRecords.map { toDomain(bookRecord = it, mainAuthors = mainAuthors) }
    }

    private fun toDomain(bookRecord: Record, mainAuthors: Map<AuthorId, AuthorDescription>): Book {
        val mainAuthorId = AuthorId(bookRecord.get(BookDsl.TABLE.MAIN_AUTHOR_ID))
        return Book(
            bookId = BookId(bookRecord.get(BookDsl.TABLE.BOOK_ID)),
            bookName = bookRecord.get(BookDsl.TABLE.BOOK_NAME),
            mainAuthor = authorDescription(mainAuthorId, mainAuthors),
        )
    }

    private fun authorDescription(authorId: AuthorId): AuthorDescription {
        return authorDescription(authorId, authorDescriptions(listOf(authorId)))
    }


    private fun authorDescription(authorId: AuthorId, authorDescriptions: Map<AuthorId, AuthorDescription>): AuthorDescription {
        return authorDescriptions[authorId] ?: throw IllegalStateException("Author not found for id $authorId")
    }

    private fun authorDescriptions(authorIds: List<AuthorId>): Map<AuthorId, AuthorDescription> {
        val authorRecords = jooqDsl
            .selectFrom(AuthorDsl.TABLE)
            .where(AuthorDsl.TABLE.AUTHOR_ID.`in`(authorIds.map { it.value }))
            .fetch()

        return authorRecords.map { record -> AuthorDescription(
            authorId = AuthorId(record.get(AuthorDsl.TABLE.AUTHOR_ID)),
            firstname = record.get(AuthorDsl.TABLE.FIRSTNAME),
            lastname = record.get(AuthorDsl.TABLE.LASTNAME),
        ) }.associateBy { author -> author.authorId }
    }
}
