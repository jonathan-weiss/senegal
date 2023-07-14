package ch.senegal.example.domain.book

import ch.senegal.example.domain.author.Author
import ch.senegal.example.domain.author.AuthorRepository
import ch.senegal.example.shareddomain.BookId
import ch.senegal.example.sharedservice.tx.Transactional
import org.springframework.stereotype.Service

@Service
class BookService(
    private val repository: BookRepository,
    private val authorRepository: AuthorRepository,
) {

    fun getBook(bookId: BookId): Book {
        return repository.fetchBookById(bookId)
    }


    @Transactional
    fun createBook(instruction: CreateBookInstruction): Book {
        val author: Author = authorRepository.fetchAuthorById(instruction.mainAuthorId)
        val book = Book.create(instruction, author)
        repository.insertBook(book)
        return getBook(book.bookId)
    }

    @Transactional
    fun updateBook(instruction: UpdateBookInstruction): Book {
        val existingEntry = repository.fetchBookById(instruction.bookId)
        val author: Author = authorRepository.fetchAuthorById(instruction.mainAuthorId)
        existingEntry.update(instruction, author)
        repository.updateBook(existingEntry)
        return getBook(instruction.bookId)
    }

    @Transactional
    fun deleteBook(instruction: DeleteBookInstruction) {
        val existingEntry = repository.fetchBookById(instruction.bookId)
        repository.deleteBook(existingEntry)
    }

    fun getBooks(): List<Book> {
        return repository.fetchAllBooks()
    }


}
