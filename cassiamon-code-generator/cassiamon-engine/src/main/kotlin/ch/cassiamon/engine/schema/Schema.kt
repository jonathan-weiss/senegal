package ch.cassiamon.engine.schema

import ch.cassiamon.pluginapi.ConceptName

data class Schema(
    val concepts: Map<ConceptName, ConceptSchema>
) {
    fun conceptByConceptName(conceptName: ConceptName): ConceptSchema {
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
