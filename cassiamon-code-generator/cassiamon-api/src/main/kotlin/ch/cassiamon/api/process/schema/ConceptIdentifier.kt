package ch.cassiamon.api.process.schema

import ch.cassiamon.api.rules.NameEnforcer
import java.util.*


class ConceptIdentifier private constructor(val code: String) {

    companion object {
        fun of(name: String): ConceptIdentifier {
            NameEnforcer.isValidIdentifierOrThrow(name)
            return ConceptIdentifier(name)
        }

        fun random(): ConceptIdentifier {
            val suffix = UUID.randomUUID().toString().replace("-", "")
            return of("Gen$suffix")
        }
    }
}
