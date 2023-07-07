package ch.cassiamon.api.model.exceptions

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.ConceptIdentifier


class ConceptNotKnownModelException(val concept: ConceptName, val conceptIdentifier: ConceptIdentifier): ModelException(
    """The entry with the identifier '${conceptIdentifier.code}' points to a concept '${concept.name}' that is not known. 
    """.trimMargin()
) {

}
