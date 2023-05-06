package ch.cassiamon.api.registration.exceptions

import ch.cassiamon.api.ConceptName


class CircularConceptHierarchieFoundSchemaException(val concept: ConceptName, val parentConcept: ConceptName): SchemaException(
    """The concept '${concept.name}' is in a circular hierarchy with '${parentConcept.name}'. 
    """.trimMargin()
) {

}
