package ch.cassiamon.api.process.templating

interface DomainUnitProcessTargetFilesData<S: Any> {
    fun getTargetFilesCollector(): TargetFilesCollector
    fun getTargetFilesWithContent(): List<TargetFileWithContent>
    fun getSchemaInstance(): S
}
