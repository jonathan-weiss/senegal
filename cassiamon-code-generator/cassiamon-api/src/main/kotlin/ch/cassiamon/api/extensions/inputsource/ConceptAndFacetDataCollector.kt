package ch.cassiamon.api.extensions.inputsource

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.ConceptIdentifier

interface ConceptAndFacetDataCollector {

    fun newConceptData(conceptName: ConceptName, conceptIdentifier: ConceptIdentifier, parentConceptIdentifier: ConceptIdentifier?, facetValues: Map<FacetName, Any?>)

}
