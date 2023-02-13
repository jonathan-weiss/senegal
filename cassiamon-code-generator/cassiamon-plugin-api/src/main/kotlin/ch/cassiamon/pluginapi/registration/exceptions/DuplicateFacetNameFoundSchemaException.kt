package ch.cassiamon.pluginapi.registration.exceptions

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName


class DuplicateFacetNameFoundSchemaException(val concept: ConceptName, val facet: FacetName): SchemaException(
    """The facet '${facet.name}' was assigned to the concept '${concept.name}' more than one time. 
    """.trimMargin()
) {

}
