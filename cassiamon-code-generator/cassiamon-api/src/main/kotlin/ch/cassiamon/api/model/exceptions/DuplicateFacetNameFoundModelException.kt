package ch.cassiamon.api.model.exceptions

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName
import ch.cassiamon.api.ConceptIdentifier


class DuplicateFacetNameFoundModelException(val concept: ConceptName, val conceptIdentifier: ConceptIdentifier, facetName: FacetName): ModelException(
    """Duplicate facet name ${facetName.name} found for concept identifier '${conceptIdentifier.code}' in concept '${concept.name}'. 
    """.trimMargin()
) {

}
