package ch.senegal.example.persistence.book

import ch.senegal.example.domain.author.Author
import ch.senegal.example.domain.author.AuthorId
import ch.senegal.example.domain.book.Book
import ch.senegal.example.domain.book.BookRepository
import ch.senegal.example.domain.book.BookId
import ch.senegal.example.persistence.author.JooqAuthorRepositoryImpl
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Repository
import java.util.*


@Repository
class JooqBookRepositoryImpl(
    private val jooqDsl: DSLContext,
    private val authorRepository: JooqAuthorRepositoryImpl
) : BookRepository {

    override fun fetchBookById(bookId: BookId): Book {
        return jooqDsl
            .selectFrom(BookDsl.TABLE)
            .where(BookDsl.TABLE.BOOK_ID.eq(bookId.value))
            .fetchOne(this::toDomain)
            ?: throw RuntimeException("Book with id \$bookId not found")
    }

    override fun fetchAllBooks(): List<Book> {
        return jooqDsl
            .selectFrom(BookDsl.TABLE)
            .fetch(this::toDomain)
    }

    override fun fetchAllBooksByAuthor(authorId: AuthorId): List<Book> {
        return fetchAllBooks()
            .filter { it.mainAuthor.authorId == authorId } // TODO do that by SQL
    }

    fun fetchAllBookFiltered(searchTerm: String): List<Book> {
        return jooqDsl
            .selectFrom(BookDsl.TABLE)
            .fetch(this::toDomain)
            // TODO Filter directly in the database by WHERE statement
            .filter {
                searchTerm.isEmpty()
                        || it.bookName.contains(searchTerm)
                        || it.bookId.value.toString().contains(searchTerm)
            }

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

    private fun toDomain(record: Record): Book {
        val mainAuthorId = AuthorId(record.get(BookDsl.TABLE.MAIN_AUTHOR_ID))
        val mainAuthor: Author = authorRepository.fetchAuthorById(mainAuthorId)
        return Book(
            bookId = BookId(record.get(BookDsl.TABLE.BOOK_ID)),
            bookName = record.get(BookDsl.TABLE.BOOK_NAME),
            mainAuthor = mainAuthor,
        )
    }
}
