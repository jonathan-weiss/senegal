package ch.cassiamon.api.registration


interface DomainUnitProcessInputData {
    fun getDataCollector(): InputSourceDataCollector

    fun getInputDataExtensionAccess(): InputSourceExtensionAccess
}
