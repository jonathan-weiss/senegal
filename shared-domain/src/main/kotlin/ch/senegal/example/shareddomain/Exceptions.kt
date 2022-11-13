package ch.senegal.example.shareddomain

import java.util.UUID

class NotFoundException(val id: Id<UUID>) : RuntimeException("Could not find entity with id: $id")
