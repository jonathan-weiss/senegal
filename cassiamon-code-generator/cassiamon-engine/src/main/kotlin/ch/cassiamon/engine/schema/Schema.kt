package ch.cassiamon.engine.schema

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.schema.ConceptSchema
import ch.cassiamon.pluginapi.schema.SchemaAccess

data class Schema(
    val concepts: Map<ConceptName, ConceptSchema>
): SchemaAccess {
    override fun conceptByConceptName(conceptName: ConceptName): ConceptSchema {
        return concepts[conceptName]
            ?: throw IllegalStateException("Concept with name '$conceptName' not found in schema: $concepts")
    }

    override fun hasConceptName(conceptName: ConceptName): Boolean {
        return concepts.containsKey(conceptName)
    }

    fun numberOfConcepts(): Int {
        return concepts.size
    }
}
