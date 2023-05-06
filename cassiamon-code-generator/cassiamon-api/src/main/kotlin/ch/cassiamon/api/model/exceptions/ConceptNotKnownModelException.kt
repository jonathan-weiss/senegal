package ch.cassiamon.api.model.exceptions

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.model.ConceptIdentifier


class ConceptNotKnownModelException(val concept: ConceptName, val conceptIdentifier: ConceptIdentifier): ModelException(
    """The entry with the identifier '${conceptIdentifier.code}' points to a concept '${concept.name}' that is not known. 
    """.trimMargin()
) {

}
