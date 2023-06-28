package ch.cassiamon.api.registration

interface DomainUnitProcessInputDataHelper {
    fun <S: Any> createDomainUnitProcessInputData(schemaDefinitionClass: Class<S>): DomainUnitProcessInputData
}
