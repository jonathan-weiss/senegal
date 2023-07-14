package ch.senegal.example.persistence.book

import ch.senegal.example.domain.author.Author
import ch.senegal.example.domain.book.Book
import ch.senegal.example.domain.book.BookRepository
import ch.senegal.example.persistence.author.AuthorJpaRepository
import ch.senegal.example.shareddomain.BookId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class BookRepositoryImpl(
    private val bookJpaRepository: BookJpaRepository,
    private val authorJpaRepository: AuthorJpaRepository
) : BookRepository {

    override fun fetchBookById(bookId: BookId): Book {
        val bookJpaEntity = bookJpaRepository.findByIdOrNull(bookId.value) ?: throw RuntimeException("Book with id $bookId not found")
        val mainAuthor = fetchAuthorByUuid(bookJpaEntity.mainAuthorId)
        return bookJpaEntity.toDomain(mainAuthor)
    }

    override fun fetchAllBooks(): List<Book> {
        return bookJpaRepository.findAll().map { it.toDomain(fetchAuthorByUuid(it.mainAuthorId)) }
    }

    override fun insertBook(book: Book) {
        bookJpaRepository.save(BookJpaEntity.fromDomain(book))
    }

    override fun updateBook(book: Book) {
        bookJpaRepository.save(BookJpaEntity.fromDomain(book))
    }

    override fun deleteBook(book: Book) {
        bookJpaRepository.delete(BookJpaEntity.fromDomain(book))
    }

    private fun fetchAuthorByUuid(authorId: UUID): Author {
        return authorJpaRepository.findByIdOrNull(authorId)?.toDomain() ?: throw RuntimeException("Author with id $authorId not found.")
    }
}
