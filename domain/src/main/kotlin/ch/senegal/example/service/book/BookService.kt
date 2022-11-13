package ch.senegal.example.service.book

import ch.senegal.example.domain.book.Book
import ch.senegal.example.shareddomain.BookId
import ch.senegal.example.sharedservice.tx.Transactional
import org.springframework.stereotype.Service

@Service
class BookService(
    private val repository: BookRepository,
) {

    fun getBook(bookId: BookId): Book {
        return repository.fetchBookById(bookId)
    }


    @Transactional
    fun createBook(instruction: CreateBookInstruction): Book {
        val book = Book.create(instruction)
        repository.insertBook(book)
        return getBook(book.bookId)
    }

    @Transactional
    fun updateBook(instruction: UpdateBookInstruction): Book {
        val existingEntry = repository.fetchBookById(instruction.bookId)
        existingEntry.update(instruction)
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
