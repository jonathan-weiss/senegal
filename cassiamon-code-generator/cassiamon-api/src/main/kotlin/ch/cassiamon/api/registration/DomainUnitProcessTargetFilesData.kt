package ch.cassiamon.api.registration

interface DomainUnitProcessTargetFilesData<S: Any> {
    fun getTargetFilesCollector(): TargetFilesCollector
    fun getSchemaInstance(): S
}
