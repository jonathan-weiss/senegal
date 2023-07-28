package ch.senegal.example.domain.book

import ch.senegal.example.shareddomain.Id
import ch.senegal.example.shareddomain.UUIDIdFactory
import java.util.*

data class BookId(override val value: UUID) : Id<UUID> {
    companion object : UUIDIdFactory<BookId>(BookId::class)
}
