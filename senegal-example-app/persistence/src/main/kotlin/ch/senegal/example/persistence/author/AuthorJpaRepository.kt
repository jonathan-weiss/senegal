package ch.senegal.example.persistence.author

import ch.senegal.example.persistence.author.AuthorJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AuthorJpaRepository : JpaRepository<AuthorJpaEntity, UUID> {

}
