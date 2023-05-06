package ch.cassiamon.api.registration.exceptions

import ch.cassiamon.api.ConceptName


class DuplicateConceptNameFoundSchemaException(val concept: ConceptName): SchemaException(
    """The concept '${concept.name}' declared more than one time. 
    """.trimMargin()
) {

}
