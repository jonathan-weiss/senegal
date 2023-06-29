package ch.cassiamon.api.registration

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.model.ConceptIdentifier

data class ConceptEntry(
    val conceptName: ConceptName,
    val conceptIdentifier: ConceptIdentifier,
    val parentConceptIdentifier: ConceptIdentifier?,
    val facetValues: Map<FacetName, Any?>,
)
