package ch.senegal.example.shareddomain

import java.util.UUID

data class BookId(override val value: UUID) : Id<UUID> {
    companion object : UUIDIdFactory<BookId>(BookId::class)
}

data class AuthorId(override val value: UUID) : Id<UUID> {
    companion object : UUIDIdFactory<AuthorId>(AuthorId::class)
}
