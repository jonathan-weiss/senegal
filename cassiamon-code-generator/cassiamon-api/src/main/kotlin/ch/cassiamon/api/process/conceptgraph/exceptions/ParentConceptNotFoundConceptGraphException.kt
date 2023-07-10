package ch.cassiamon.api.process.conceptgraph.exceptions

import ch.cassiamon.api.process.schema.ConceptIdentifier
import ch.cassiamon.api.process.schema.ConceptName


class ParentConceptNotFoundConceptGraphException(val conceptName: ConceptName, val conceptIdentifier: ConceptIdentifier, val parentConceptIdentifier: ConceptIdentifier): ConceptGraphException(
    """Concept with identifier '${conceptIdentifier.code}' (${conceptName.name}) could not found its parent concept '${parentConceptIdentifier.code}'. 
    """.trimMargin()
)
