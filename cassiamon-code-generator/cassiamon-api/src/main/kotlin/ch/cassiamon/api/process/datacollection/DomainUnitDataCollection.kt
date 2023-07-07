package ch.cassiamon.api.process.datacollection

import ch.cassiamon.api.process.datacollection.extensions.DataCollectionExtensionAccess


interface DomainUnitDataCollection<I: Any> {
    fun getDataCollector(): I

    fun getDataCollectionExtensionAccess(): DataCollectionExtensionAccess

    fun getCollectedData(): List<ConceptData>
}
