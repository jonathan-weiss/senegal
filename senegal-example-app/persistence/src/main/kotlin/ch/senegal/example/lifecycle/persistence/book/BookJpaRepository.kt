package ch.senegal.example.lifecycle.persistence.book

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BookJpaRepository : JpaRepository<BookJpaEntity, UUID> {

}