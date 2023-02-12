package ch.cassiamon.engine.model

import ch.cassiamon.engine.schema.WiredConceptSchema
import ch.cassiamon.pluginapi.ConceptSchema

class ConceptInputEntry(
    val wiredConceptSchema: WiredConceptSchema,
    val conceptIdentifier: ConceptIdentifier,
    val facetInputEntries: Set<FacetInputEntry>
)
