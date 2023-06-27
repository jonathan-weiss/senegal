package ch.cassiamon.api.registration

interface DomainUnitProcessSession<S: Any> {
    fun getDataCollector(): InputSourceDataCollector

    fun getInputDataExtensionAccess(): InputSourceExtensionAccess

    fun getTargetFilesCollector(): TargetFilesCollector
    fun getSchemaInstance(): S
}
