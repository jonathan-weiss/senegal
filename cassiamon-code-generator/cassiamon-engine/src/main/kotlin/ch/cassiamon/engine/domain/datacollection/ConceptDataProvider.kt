package ch.cassiamon.engine.domain.datacollection

import ch.cassiamon.api.registration.ConceptData

interface ConceptDataProvider {
    fun provideConceptData(): List<ConceptData>
}
