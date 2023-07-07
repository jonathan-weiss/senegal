package ch.cassiamon.api.model.exceptions

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.ConceptIdentifier


class MissingFacetValueModelException(
    concept: ConceptName,
    conceptIdentifier: ConceptIdentifier,
    facetName: FacetName): ConceptRelatedModelException(concept, conceptIdentifier,
    """The following facet has no value: [${facetName.name}]. 
    """.trimMargin()
) {

}
