package ch.cassiamon.api.datacollection.extensions

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.ConceptIdentifier

interface ConceptAndFacetDataCollector {

    fun newConceptData(conceptName: ConceptName, conceptIdentifier: ConceptIdentifier, parentConceptIdentifier: ConceptIdentifier?, facetValues: Map<FacetName, Any?>)

}
