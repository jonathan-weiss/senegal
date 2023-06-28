package ch.cassiamon.api.registration

interface DomainUnitProcessTargetFilesHelper {
    fun <S: Any> createDomainUnitProcessTargetFilesData(schemaDefinitionClass: Class<S>): DomainUnitProcessTargetFilesData<S>

}
