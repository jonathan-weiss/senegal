package ch.cassiamon.pluginapi.registration.exceptions

import ch.cassiamon.pluginapi.ConceptName


class UnknownParentConceptFoundSchemaException(val concept: ConceptName, val parentConcept: ConceptName): SchemaException(
    """The concept '${concept.name}' has a unknown parent concept '${parentConcept.name}'. 
    """.trimMargin()
) {

}
