package ch.cassiamon.api.templating

interface DomainUnitProcessTargetFilesData<S: Any> {
    fun getTargetFilesCollector(): TargetFilesCollector
    fun getSchemaInstance(): S
}
