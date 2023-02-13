package ch.cassiamon.pluginapi.registration.exceptions

import ch.cassiamon.pluginapi.ConceptName


class CircularConceptHierarchieFoundSchemaException(val concept: ConceptName, val parentConcept: ConceptName): SchemaException(
    """The concept '${concept.name}' is in a circular hierarchy with '${parentConcept.name}'. 
    """.trimMargin()
) {

}
