package ch.cassiamon.api.process.datacollection.extensions

import ch.cassiamon.api.process.datacollection.ConceptData
import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.schema.ConceptIdentifier

interface ExtensionDataCollector {

    fun existingConceptData(
        conceptIdentifier: ConceptIdentifier
    ): ConceptData

    fun existingOrNewConceptData(
        conceptName: ConceptName,
        conceptIdentifier: ConceptIdentifier,
        parentConceptIdentifier: ConceptIdentifier? = null
    ): ConceptData

}
