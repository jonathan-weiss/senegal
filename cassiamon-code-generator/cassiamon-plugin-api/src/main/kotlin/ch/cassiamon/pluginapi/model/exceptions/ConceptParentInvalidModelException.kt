package ch.cassiamon.pluginapi.model.exceptions

import ch.cassiamon.pluginapi.ConceptName
import ch.cassiamon.pluginapi.model.ConceptIdentifier


class ConceptParentInvalidModelException(val concept: ConceptName,
                                         val conceptIdentifier: ConceptIdentifier,
                                         val parentConceptIdentifier: ConceptIdentifier?,
    ): ModelException(
    """The entry with the identifier '${conceptIdentifier.code}' ('${concept.name}') 
        has an invalid parent concept identifier '${parentConceptIdentifier?.code}'. 
    """.trimMargin()
) {

}
