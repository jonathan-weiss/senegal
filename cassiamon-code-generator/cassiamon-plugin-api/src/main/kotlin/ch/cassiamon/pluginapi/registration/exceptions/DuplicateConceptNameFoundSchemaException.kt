package ch.cassiamon.pluginapi.registration.exceptions

import ch.cassiamon.pluginapi.ConceptName


class DuplicateConceptNameFoundSchemaException(val concept: ConceptName): SchemaException(
    """The concept '${concept.name}' declared more than one time. 
    """.trimMargin()
) {

}
