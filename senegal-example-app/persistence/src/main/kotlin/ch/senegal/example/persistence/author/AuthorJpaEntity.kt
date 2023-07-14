package ch.senegal.example.persistence.author

import ch.senegal.example.domain.author.Author
import ch.senegal.example.shareddomain.AuthorId
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "AUTHOR")
class AuthorJpaEntity(

    @Id
    @Column(name = "ID")
    val id: UUID,

    @Column(name = "FIRSTNAME")
    val firstname: String,

    @Column(name = "LASTNAME")
    val lastname: String,

    ) {

    companion object {
        fun fromDomain(author: Author) =
            AuthorJpaEntity(
                id = author.authorId.value,
                firstname = author.firstname,
                lastname = author.lastname,
            )
    }

    internal fun toDomain() = Author(
        authorId = AuthorId(this.id),
        firstname = this.firstname,
        lastname = this.lastname,
    )
}
