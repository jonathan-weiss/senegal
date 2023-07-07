package ch.cassiamon.api.model.exceptions

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.ConceptIdentifier


class ConceptParentInvalidModelException(val concept: ConceptName,
                                         val conceptIdentifier: ConceptIdentifier,
                                         val parentConceptIdentifier: ConceptIdentifier?,
    ): ModelException(
    """The entry with the identifier '${conceptIdentifier.code}' ('${concept.name}') 
        has an invalid parent concept identifier '${parentConceptIdentifier?.code}'. 
    """.trimMargin()
) {

}
