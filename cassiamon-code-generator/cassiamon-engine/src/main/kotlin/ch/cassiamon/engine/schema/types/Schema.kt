package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName

data class Schema(
    val concepts: Map<ConceptName, Concept>
) {
    fun conceptByConceptName(conceptName: ConceptName): Concept {
        return concepts[conceptName]
            ?: throw IllegalStateException("Concept with name '$conceptName' not found in schema: $concepts")
    }

    fun hasConceptName(conceptName: ConceptName): Boolean {
        return concepts.containsKey(conceptName)
    }

    fun numberOfConcepts(): Int {
        return concepts.size
    }
}
