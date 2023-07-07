package ch.cassiamon.api.model.exceptions

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.ConceptIdentifier


class UnknownFacetNameFoundModelException(val concept: ConceptName, val conceptIdentifier: ConceptIdentifier, facetName: FacetName): ModelException(
    """Unknown facet name ${facetName.name} found for concept identifier '${conceptIdentifier.code}' in concept '${concept.name}'. 
    """.trimMargin()
) {

}
