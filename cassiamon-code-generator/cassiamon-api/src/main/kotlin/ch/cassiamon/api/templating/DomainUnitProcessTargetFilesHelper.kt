package ch.cassiamon.api.templating

interface DomainUnitProcessTargetFilesHelper {
    fun <S: Any> createDomainUnitProcessTargetFilesData(schemaDefinitionClass: Class<S>): DomainUnitProcessTargetFilesData<S>

}
