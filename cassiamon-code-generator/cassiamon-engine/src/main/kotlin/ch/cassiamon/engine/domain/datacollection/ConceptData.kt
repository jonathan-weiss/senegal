package ch.cassiamon.engine.domain.datacollection

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.ConceptIdentifier

interface ConceptData {
    val conceptName: ConceptName
    val conceptIdentifier: ConceptIdentifier
    val parentConceptIdentifier: ConceptIdentifier?
    val facets: Map<FacetName, Any?>
}
