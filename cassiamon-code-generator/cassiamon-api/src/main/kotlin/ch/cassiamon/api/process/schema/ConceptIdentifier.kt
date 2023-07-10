package ch.cassiamon.api.process.schema

import ch.cassiamon.api.ComparableId
import ch.cassiamon.api.rules.NameEnforcer
import java.util.*


class ConceptIdentifier private constructor(name: String): ComparableId(name = name) {

    init {
        NameEnforcer.isValidIdentifierOrThrow(name)
    }

    companion object {
        fun of(code: String): ConceptIdentifier {
            return ConceptIdentifier(code)
        }

        fun random(): ConceptIdentifier {
            val suffix = UUID.randomUUID().toString().replace("-", "")
            return of("Gen$suffix")
        }
    }
}
