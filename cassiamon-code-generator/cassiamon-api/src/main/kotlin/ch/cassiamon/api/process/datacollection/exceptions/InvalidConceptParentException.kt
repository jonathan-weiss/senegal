package ch.cassiamon.api.process.datacollection.exceptions

import ch.cassiamon.api.process.schema.ConceptIdentifier
import ch.cassiamon.api.process.schema.ConceptName


class InvalidConceptParentException(val concept: ConceptName,
                                    val conceptIdentifier: ConceptIdentifier,
                                    val parentConceptIdentifier: ConceptIdentifier?,
    ): SchemaValidationException(
    "The entry with the identifier '${conceptIdentifier.name}' ('${concept.name}') has an invalid parent concept identifier '${parentConceptIdentifier?.name}'."
)
