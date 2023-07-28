package ch.senegal.example.domain.book


interface BookRepository {
    fun fetchBookById(bookId: BookId): Book
    fun fetchAllBooks(): List<Book>

    fun insertBook(book: Book)
    fun updateBook(book: Book)
    fun deleteBook(book: Book)
}
