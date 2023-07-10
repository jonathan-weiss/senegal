package ch.cassiamon.api.process.conceptgraph.exceptions

import ch.cassiamon.api.process.schema.ConceptIdentifier
import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName

class ReferencedConceptConceptGraphNodeNotFoundException(
    val conceptIdentifier: ConceptIdentifier,
    conceptName: ConceptName,
    facetName: FacetName,
    referencedConceptIdentifier: ConceptIdentifier
): ConceptGraphException(
    """Concept with identifier '${conceptIdentifier.code}' (${conceptName.name}) has a facet '${facetName.name}' referencing a concept '${referencedConceptIdentifier.code}' that could not be found. 
    """.trimMargin()
)
