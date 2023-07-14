package ch.senegal.example.persistence.book

import ch.senegal.example.domain.author.Author
import ch.senegal.example.domain.book.Book
import ch.senegal.example.shareddomain.BookId
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "BOOK")
class BookJpaEntity(

    @Id
    @Column(name = "ID")
    val id: UUID,

    @Column(name = "BOOK_NAME")
    val bookName: String,

    @Column(name = "MAIN_AUTHOR_ID")
    val mainAuthorId: UUID,

    ) {

    companion object {
        fun fromDomain(book: Book) =
            BookJpaEntity(
                id = book.bookId.value,
                bookName = book.bookName,
                mainAuthorId = book.mainAuthor.authorId.value
            )
    }

    internal fun toDomain(mainAuthor: Author) = Book(
        bookId = BookId(this.id),
        bookName = this.bookName,
        mainAuthor = mainAuthor
    )
}
