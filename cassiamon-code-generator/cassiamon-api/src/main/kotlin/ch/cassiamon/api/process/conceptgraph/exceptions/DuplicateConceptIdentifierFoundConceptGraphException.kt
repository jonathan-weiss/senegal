package ch.cassiamon.api.process.conceptgraph.exceptions

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.ConceptIdentifier


class DuplicateConceptIdentifierFoundConceptGraphException(val concept: ConceptName, val conceptIdentifier: ConceptIdentifier): ConceptGraphException(
    "Duplicate concept identifier '${conceptIdentifier.code}' in concept '${concept.name}'."
)
