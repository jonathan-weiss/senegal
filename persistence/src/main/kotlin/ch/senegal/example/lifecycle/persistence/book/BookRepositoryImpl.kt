package ch.senegal.example.lifecycle.persistence.book

import ch.senegal.example.domain.book.Book
import ch.senegal.example.service.book.BookRepository
import ch.senegal.example.shareddomain.BookId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class BookRepositoryImpl(
    private val jpaRepository: BookJpaRepository
) : BookRepository {

    override fun fetchBookById(bookId: BookId): Book {
        val entry = jpaRepository.findByIdOrNull(bookId.value) ?: throw RuntimeException("Book with id $bookId not found")
        return entry.toDomain()
    }

    override fun fetchAllBooks(): List<Book> {
        return jpaRepository.findAll().map { it.toDomain() }
    }

    override fun insertBook(book: Book) {
        jpaRepository.save(BookJpaEntity.fromDomain(book))
    }

    override fun updateBook(book: Book) {
        jpaRepository.save(BookJpaEntity.fromDomain(book))
    }

    override fun deleteBook(book: Book) {
        jpaRepository.delete(BookJpaEntity.fromDomain(book))
    }
}
