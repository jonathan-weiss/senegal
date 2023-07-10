package ch.cassiamon.api.process.schema

import ch.cassiamon.api.NamedId
import java.util.*


class ConceptIdentifier private constructor(val code: String): NamedId(code) {

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
