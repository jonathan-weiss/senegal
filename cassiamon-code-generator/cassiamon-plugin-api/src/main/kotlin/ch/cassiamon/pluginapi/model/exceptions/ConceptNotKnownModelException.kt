package ch.cassiamon.pluginapi.model.exceptions

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier


class ConceptNotKnownModelException(val concept: ConceptName, val conceptIdentifier: ConceptIdentifier): ModelException(
    """The entry with the identifier '${conceptIdentifier.code}' points to a concept '${concept.name}' that is not known. 
    """.trimMargin()
) {

}
