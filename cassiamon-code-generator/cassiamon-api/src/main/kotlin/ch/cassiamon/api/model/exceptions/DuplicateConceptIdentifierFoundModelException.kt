package ch.cassiamon.api.model.exceptions

import ch.cassiamon.api.ConceptName
import ch.cassiamon.api.ConceptIdentifier


class DuplicateConceptIdentifierFoundModelException(val concept: ConceptName, val conceptIdentifier: ConceptIdentifier): ModelException(
    """Duplicate concept identifier '${conceptIdentifier.code}' in concept '${concept.name}'. 
    """.trimMargin()
) {

}
