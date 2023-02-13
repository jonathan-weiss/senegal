package ch.cassiamon.engine.schema.types

import ch.cassiamon.pluginapi.ConceptName


interface Concept {
    val conceptName: ConceptName
    val parentConceptName: ConceptName?
    val facets: List<Facet>
}
