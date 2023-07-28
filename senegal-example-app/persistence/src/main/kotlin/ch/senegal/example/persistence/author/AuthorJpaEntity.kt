package ch.senegal.example.persistence.author

import ch.senegal.example.domain.author.Author
import ch.senegal.example.domain.author.AuthorId
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "AUTHOR")
class AuthorJpaEntity(
    @Id
    @Column(name = "AUTHOR_ID")
    val authorId: UUID,
    
    
    @Column(name = "FIRSTNAME")
    val firstname: kotlin.String,

    @Column(name = "LASTNAME")
    val lastname: kotlin.String,

) {
    companion object {
        fun fromDomain(domainInstance: Author) = AuthorJpaEntity(
            authorId = domainInstance.authorId.value,
            firstname = domainInstance.firstname,
            
            lastname = domainInstance.lastname,
            
        )
    }

    fun toDomain() = Author(
        authorId = AuthorId(this.authorId),
        firstname = this.firstname,
        
        lastname = this.lastname,
        
    )

}
