package ch.cassiamon.api.registration.exceptions

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.FacetName


class DuplicateFacetNameFoundSchemaException(val concept: ConceptName, val facetName: FacetName): SchemaException(
    """The facet '${facetName.name}' was assigned to the concept '${concept.name}' more than one time. 
    """.trimMargin()
) {

}
