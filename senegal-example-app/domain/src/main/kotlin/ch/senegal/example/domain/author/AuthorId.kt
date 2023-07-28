package ch.senegal.example.domain.author

import ch.senegal.example.shareddomain.Id
import ch.senegal.example.shareddomain.UUIDIdFactory
import java.util.UUID

data class AuthorId(override val value: UUID) : Id<UUID> {
    companion object : UUIDIdFactory<AuthorId>(AuthorId::class)
}
