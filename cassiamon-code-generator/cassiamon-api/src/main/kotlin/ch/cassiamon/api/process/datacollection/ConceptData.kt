package ch.cassiamon.api.process.datacollection

import ch.cassiamon.api.process.schema.ConceptName
import ch.cassiamon.api.process.schema.FacetName
import ch.cassiamon.api.process.schema.ConceptIdentifier

interface ConceptData {
    val conceptName: ConceptName
    val conceptIdentifier: ConceptIdentifier
    val parentConceptIdentifier: ConceptIdentifier?
    val facets: Map<FacetName, Any?>
}
