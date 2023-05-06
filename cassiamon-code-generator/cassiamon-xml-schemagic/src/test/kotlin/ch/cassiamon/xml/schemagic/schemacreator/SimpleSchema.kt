package ch.cassiamon.xml.schemagic.schemacreator

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.schema.ConceptSchema
import ch.cassiamon.api.schema.SchemaAccess

class SimpleSchema(conceptList: List<ConceptSchema>): SchemaAccess {

    private val concepts: Map<ConceptName, ConceptSchema> = conceptList.associateBy { it.conceptName }
    override fun conceptByConceptName(conceptName: ConceptName): ConceptSchema {
        return concepts[conceptName]
            ?: throw IllegalStateException("Concept with name '$conceptName' not found in schema: $concepts")
    }

    override fun hasConceptName(conceptName: ConceptName): Boolean {
        return concepts.containsKey(conceptName)
    }

    override fun allConcepts(): Set<ConceptSchema> {
        return concepts.values.toSet()
    }

    override fun allRootConcepts(): Set<ConceptSchema> {
        return allConcepts().filter { it.parentConceptName == null }.toSet()
    }

    override fun allChildrenConcepts(concept: ConceptSchema): Set<ConceptSchema> {
        return allConcepts().filter { it.parentConceptName == concept.conceptName }.toSet()
    }
}

