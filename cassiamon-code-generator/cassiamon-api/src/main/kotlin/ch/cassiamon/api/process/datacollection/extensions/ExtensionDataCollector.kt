package ch.cassiamon.api.process.datacollection.extensions

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.schema.ConceptIdentifier

interface ExtensionDataCollector {

    fun newConceptData(conceptName: ConceptName, conceptIdentifier: ConceptIdentifier, parentConceptIdentifier: ConceptIdentifier?, facetValues: Map<FacetName, Any?>)

}
