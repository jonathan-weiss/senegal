package ch.cassiamon.pluginapi.model.exceptions

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName
import ch.cassiamon.pluginapi.model.ConceptIdentifier


class DuplicateFacetNameFoundModelException(val concept: ConceptName, val conceptIdentifier: ConceptIdentifier, facetName: FacetName): ModelException(
    """Duplicate facet name ${facetName.name} found for concept identifier '${conceptIdentifier.code}' in concept '${concept.name}'. 
    """.trimMargin()
) {

}
