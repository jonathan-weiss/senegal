package org.codeblessing.senegal.domain.book

import org.codeblessing.senegal.shareddomain.Id
import org.codeblessing.senegal.shareddomain.UUIDIdFactory
import java.util.*

data class BookId(override val value: UUID) : Id<UUID> {
    companion object : UUIDIdFactory<BookId>(BookId::class)
}
