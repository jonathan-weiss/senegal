package ch.cassiamon.api.datacollection

import ch.cassiamon.api.registration.InputSourceExtensionAccess


interface DomainUnitDataCollection<I: Any> {
    fun getDataCollector(): I

    fun getInputDataExtensionAccess(): InputSourceExtensionAccess

    fun provideConceptEntries(): List<ConceptData>
}
