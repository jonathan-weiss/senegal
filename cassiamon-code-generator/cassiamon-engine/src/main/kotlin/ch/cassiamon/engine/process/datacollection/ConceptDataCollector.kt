package ch.cassiamon.engine.process.datacollection

import ch.cassiamon.api.process.schema.ConceptIdentifier
import ch.cassiamon.api.process.datacollection.extensions.ExtensionDataCollector
import ch.cassiamon.api.process.datacollection.ConceptData
import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.schema.SchemaAccess

class ConceptDataCollector(private val schema: SchemaAccess, private val validateConcept: Boolean = true):
    ExtensionDataCollector {

    private val conceptData: MutableMap<ConceptIdentifier, ConceptData> = mutableMapOf()

    override fun existingConceptData(conceptIdentifier: ConceptIdentifier): ConceptData {
        return conceptData[conceptIdentifier] ?: throw IllegalArgumentException("No concept with concept id '$conceptIdentifier' found.")
    }

    override fun existingOrNewConceptData(conceptName: ConceptName, conceptIdentifier: ConceptIdentifier, parentConceptIdentifier: ConceptIdentifier?): ConceptData {
        return conceptData.getOrPut(conceptIdentifier) {
            ConceptDataImpl(conceptName, conceptIdentifier)
        }.setParentConceptIdentifier(parentConceptIdentifier)
    }

    fun provideConceptData(): List<ConceptData> {
        return conceptData.values.toList()
    }
}
