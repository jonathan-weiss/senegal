package ch.cassiamon.pluginapi.registration.exceptions

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetDescriptor
import ch.cassiamon.pluginapi.FacetName


class DuplicateFacetNameFoundSchemaException(val concept: ConceptName, val facetName: FacetName): SchemaException(
    """The facet '${facetName.name}' was assigned to the concept '${concept.name}' more than one time. 
    """.trimMargin()
) {

}
