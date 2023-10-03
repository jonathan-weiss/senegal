package org.codeblessing.senegal.shareddomain

import java.lang.reflect.Constructor
import java.util.UUID
import kotlin.reflect.KClass

/**
 * Base interface for strongly-typed identifiers
 * Implementors are expected to
 *
 * * be data classes with proper equals/hashCode/toString implementations
 * * provide a constructor with 1 parameter of type @param WRAPPED
 */
interface Id<out WRAPPED> {
    val value: WRAPPED
}

abstract class IdFactory<ID : Id<WRAPPED>, WRAPPED> private constructor(
    private val idConstructor: Constructor<ID>,
) {
    constructor(idKlass: KClass<ID>, rawClass: Class<WRAPPED>) : this(idKlass.java.getConstructor(rawClass))

    protected fun createId(value: WRAPPED): ID = idConstructor.newInstance(value)
}

abstract class UUIDIdFactory<ID : Id<UUID>> constructor(idKlass: KClass<ID>) :
    IdFactory<ID, UUID>(idKlass, UUID::class.java) {

    fun ofNullable(value: UUID?): ID? = value?.let { createId(it) }

    fun random(): ID = createId(UUID.randomUUID())

    fun parse(value: String): ID = createId(UUID.fromString(value))

    fun parseNullable(value: String?): ID? = value?.let { createId(UUID.fromString(it)) }
}
