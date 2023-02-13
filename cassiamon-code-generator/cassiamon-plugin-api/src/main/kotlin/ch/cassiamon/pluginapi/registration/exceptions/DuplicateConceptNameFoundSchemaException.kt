package ch.cassiamon.pluginapi.registration.exceptions

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.FacetName


class DuplicateConceptNameFoundSchemaException(val concept: ConceptName): SchemaException(
    """The concept '${concept.name}' declared more than one time. 
    """.trimMargin()
) {

}
