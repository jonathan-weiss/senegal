package ch.cassiamon.pluginapi.schema

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.schema.ConceptSchema

interface SchemaAccess {
    fun conceptByConceptName(conceptName: ConceptName): ConceptSchema

    fun hasConceptName(conceptName: ConceptName): Boolean

    fun allConcepts(): Set<ConceptSchema>

    fun allRootConcepts(): Set<ConceptSchema>
    fun allChildrenConcepts(concept: ConceptSchema): Set<ConceptSchema>
}
