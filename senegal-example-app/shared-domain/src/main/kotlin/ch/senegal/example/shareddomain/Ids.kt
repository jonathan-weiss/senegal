package ch.senegal.example.shareddomain

import java.util.UUID

data class BookId(override val value: UUID) : Id<UUID> {
    companion object : UUIDIdFactory<BookId>(BookId::class)
}
