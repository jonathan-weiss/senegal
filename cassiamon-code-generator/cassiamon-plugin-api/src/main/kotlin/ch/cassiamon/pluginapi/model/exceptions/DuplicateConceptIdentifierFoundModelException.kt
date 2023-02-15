package ch.cassiamon.pluginapi.model.exceptions

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier


class DuplicateConceptIdentifierFoundModelException(val concept: ConceptName, val conceptIdentifier: ConceptIdentifier): ModelException(
    """Duplicate concept identifier '${conceptIdentifier.code}' in concept '${concept.name}'. 
    """.trimMargin()
) {

}
