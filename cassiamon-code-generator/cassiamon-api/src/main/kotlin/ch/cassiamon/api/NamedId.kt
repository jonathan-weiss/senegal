package ch.cassiamon.api

import ch.cassiamon.api.rules.NameEnforcer

abstract class NamedId protected constructor(val name: String) {

    init {
        NameEnforcer.isValidNameOrThrow(name)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if(other == null) return false
        if(other::class != this::class) return false

        if (other is NamedId) {
            return name == other.name
        }
        return false
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return "${this.javaClass.simpleName}:$name"
    }

}
