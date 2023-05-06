package ch.cassiamon.api.model.exceptions

import ch.cassiamon.api.model.ConceptIdentifier


class ConceptNotFoundModelException(val conceptIdentifier: ConceptIdentifier): ModelException(
    """Concept with identifier '${conceptIdentifier.code}' was not found. 
    """.trimMargin()
) {

}
