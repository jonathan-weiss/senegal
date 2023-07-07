package ch.cassiamon.engine.process.datacollection

import ch.cassiamon.api.process.datacollection.ConceptData

interface ConceptDataProvider {
    fun provideConceptData(): List<ConceptData>
}
