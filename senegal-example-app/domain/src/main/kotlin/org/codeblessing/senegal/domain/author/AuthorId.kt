package org.codeblessing.senegal.domain.author

import org.codeblessing.senegal.shareddomain.Id
import org.codeblessing.senegal.shareddomain.UUIDIdFactory
import java.util.UUID

data class AuthorId(override val value: UUID) : Id<UUID> {
    companion object : UUIDIdFactory<AuthorId>(AuthorId::class)
}
