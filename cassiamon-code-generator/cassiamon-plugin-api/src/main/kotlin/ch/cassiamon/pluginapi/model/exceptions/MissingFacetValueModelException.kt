package ch.cassiamon.pluginapi.model.exceptions

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptIdentifier


class MissingFacetValueModelException(
    concept: ConceptName,
    conceptIdentifier: ConceptIdentifier,
    facetName: FacetName): ConceptRelatedModelException(concept, conceptIdentifier,
    """The following facet has no value: [$facetName]. 
    """.trimMargin()
) {

}
