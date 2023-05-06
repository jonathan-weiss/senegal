package ch.cassiamon.api.registration.exceptions

import ch.cassiamon.api.ConceptName


class UnknownParentConceptFoundSchemaException(val concept: ConceptName, val parentConcept: ConceptName): SchemaException(
    """The concept '${concept.name}' has a unknown parent concept '${parentConcept.name}'. 
    """.trimMargin()
) {

}
