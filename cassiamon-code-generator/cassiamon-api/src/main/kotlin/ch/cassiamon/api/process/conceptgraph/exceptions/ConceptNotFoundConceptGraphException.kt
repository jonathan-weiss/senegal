package ch.cassiamon.api.process.conceptgraph.exceptions

import ch.cassiamon.api.process.schema.ConceptIdentifier


class ConceptNotFoundConceptGraphException(val conceptIdentifier: ConceptIdentifier): ConceptGraphException(
    """Concept with identifier '${conceptIdentifier.code}' was not found. 
    """.trimMargin()
) {

}
