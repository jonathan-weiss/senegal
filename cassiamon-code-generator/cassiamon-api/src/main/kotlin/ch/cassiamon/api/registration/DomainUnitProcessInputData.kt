package ch.cassiamon.api.registration


interface DomainUnitProcessInputData<I: Any> {
    fun getDataCollector(): I

    fun getInputDataExtensionAccess(): InputSourceExtensionAccess

    fun provideConceptEntries(): ConceptEntries
}
