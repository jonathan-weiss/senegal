package ch.cassiamon.api.model.exceptions

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.schema.ConceptIdentifier


class MissingFacetValueModelException(
    concept: ConceptName,
    conceptIdentifier: ConceptIdentifier,
    facetName: FacetName
): ConceptRelatedModelException(concept, conceptIdentifier,
    """The following facet has no value: [${facetName.name}]. 
    """.trimMargin()
) {

}
