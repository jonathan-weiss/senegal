package ch.cassiamon.api.datacollection

import ch.cassiamon.api.datacollection.extensions.DataCollectionExtensionAccess


interface DomainUnitDataCollection<I: Any> {
    fun getDataCollector(): I

    fun getInputDataExtensionAccess(): DataCollectionExtensionAccess

    fun provideConceptEntries(): List<ConceptData>
}
