package ch.cassiamon.engine.domain.datacollection

import ch.cassiamon.api.datacollection.ConceptData

interface ConceptDataProvider {
    fun provideConceptData(): List<ConceptData>
}
