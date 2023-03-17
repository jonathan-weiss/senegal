package ch.cassiamon.pluginapi.model.exceptions

import ch.cassiamon.pluginapi.model.ConceptIdentifier


class ConceptNotFoundModelException(val conceptIdentifier: ConceptIdentifier): ModelException(
    """Concept with identifier '${conceptIdentifier.code}' was not found. 
    """.trimMargin()
) {

}
